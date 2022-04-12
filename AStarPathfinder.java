import java.util.HashMap;
import java.util.HashSet;

/**
 * Этот класс содержит реализацию алгоритма поиска пути A*.
 * Алгоритм реализован как статический метод,
 * поскольку алгоритму поиска пути действительно не нужно поддерживать
 * какое-либо состояние между вызовами алгоритма.
 */
public class AStarPathfinder
{
    /**
     * Эта константа содержит максимальное ограничение стоимости путей.
     * Если конкретная путевая точка превышает этот предел стоимости,
     * путевая точка отбрасывается.
     **/
    public static final float COST_LIMIT = 1e6f;


    /**
     * Пытается вычислить путь, который проходит между
     * начальным и конечным местоположениями указанной карты.
     * Если путь найден, возвращается путевая точка <em>последнего</em>
     * шага пути; эту путевую точку можно использовать,
     * чтобы вернуться к исходной точке. Если путь не найден,
     * возвращается <code>null</code>.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для поиска A*.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Установите начальную путевую точку, чтобы начать поиск A *.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;

        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Найдите «лучшую» (т. е. самую дешевую) путевую точку на данный момент.
            Waypoint best = state.getMinOpenWaypoint();

            // Если лучшее место - это место финиша, то мы закончили!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }

            // Добавить/обновить всех соседей текущего лучшего местоположения.
            // Это эквивалентно попытке выполнить все «следующие шаги» из этого места.
            takeNextStep(best, state);

            // Наконец, переместите это место из списка «открытых» в список «закрытых».
            state.closeWaypoint(best.getLocation());
        }

        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод принимает
     * путевую точку и генерирует все допустимые «следующие шаги»
     * из этой путевой точки. Новые путевые точки добавляются
     * в коллекцию «открытых путевых точек» переданного объекта состояния A*.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();

        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);

                // Если «следующее место» находится за пределами карты, пропустите его.
                if (!map.contains(nextLoc))
                    continue;

                // Если «следующее место» — это место, пропустите его.
                if (nextLoc == loc)
                    continue;

                // Если это местоположение уже находится в «закрытом» наборе,
                // перейдите к следующему местоположению.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Сделайте путевую точку для этого «следующего места».

                Waypoint nextWP = new Waypoint(nextLoc, currWP);

                // Хорошо, мы обманываем и используем оценку стоимости для
                // вычисления фактической стоимости из предыдущей ячейки.
                // Затем мы добавляем стоимость ячейки карты, на которую мы ступаем, чтобы включить барьеры и т. д.

                float prevCost = currWP.getPreviousCost() +
                        estimateTravelCost(currWP.getLocation(),
                                nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);

                // Пропустите это «следующее место», если это слишком дорого.
                if (prevCost >= COST_LIMIT)
                    continue;

                nextWP.setCosts(prevCost,
                        estimateTravelCost(nextLoc, map.getFinish()));

                // Добавьте путевую точку в набор открытых путевых точек.
                // Если для этого места уже есть путевая точка,
                // новая путевая точка заменяет старую путевую точку
                // только в том случае, если она менее затратна, чем старая.

                state.addOpenWaypoint(nextWP);
            }
        }
    }

    /**
     * Оценивает стоимость проезда между двумя указанными местами.
     * Фактическая рассчитанная стоимость — это просто
     * расстояние по прямой между двумя местоположениями.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}