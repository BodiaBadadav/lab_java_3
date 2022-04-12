import java.util.*;

public class AStarState
{

    HashMap<Location, Waypoint> open_waypoints = new HashMap();//открытые точки
    HashMap<Location, Waypoint> closed_waypoints = new HashMap();//закрытые точки


    /** Это ссылка на карту, по которой алгоритм A* перемещается. **/
    private Map2D map;


    /**
     * Инициализируем новый объект состояния для использования алгоритма поиска пути A*.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку
     * с минимальной общей стоимостью.  Если открытых путевых точек нет, этот метод
     * возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0)
            return null;

        // инициализируем набор ключей всех открытых waypoint, 
        //итератор для итерации по набору
        //и переменную для хранения лучшей waypoint и стоимости этой путевой точки.
        Set open_waypoint_keys = open_waypoints.keySet();
        Iterator i = open_waypoint_keys.iterator();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;

        // сканируем все открытые waypoints.
        while (i.hasNext())
        {
            // сохраняет текущее местоположение.
            Location location = (Location)i.next();
            // сохраняет текущий waypoint.
            Waypoint waypoint = open_waypoints.get(location);
            // сохраняет общую стоимость текущего waypoint.
            float waypoint_total_cost = waypoint.getTotalCost();

            // если общая стоимость для текущей waypoint  лучше (ниже)
            // чем сохраненная стоимость для сохраненной лучшей waypoint, то обменяем их
            if (waypoint_total_cost < best_cost)
            {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }

        }
        // возвращает путевую точку с минимальной общей стоимостью.
        return best;
    }
    /**
     * Этот метод добавляет путевую точку к (или потенциально обновляет уже имеющуюся путевую точку
     * в) коллекции "открытые путевые точки".  Если еще нет открытой
     * путевой точки в местоположении новой путевой точки, тогда новая путевая точка просто
     * добавляется в коллекцию.  Однако, если в местоположении
     * новой путевой точки уже есть путевая точка, новая путевая точка заменяет только старую <em>
     * если</em> значение "предыдущей стоимости" новой путевой точки меньше текущего
     * значение "предыдущей стоимости" путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        // Находит местонахождение нового waypoint.
        Location location = newWP.getLocation();

        // Проверяет, есть ли уже открытая путевая точка в новом
        // местоположении путевой точки.
        if (open_waypoints.containsKey(location))
        {
            // Если в новой путевой точке уже есть открытая путевая точка
            // местоположения, проверяет, является ли у новой путевая точка предыдущее
            // значение "стоимости" меньше, чем предыдущее текущей путевой точки
            // стоимость їзначение.
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                // Если значение "предыдущей стоимости" новой путевой точки меньше, чем
                // значение "предыдущей стоимости" текущей путевой точки, новая путевая точка
                // заменяет старую путевую точку и возвращает true.
                open_waypoints.put(location, newWP);
                return true;
            }
            // Если значение "предыдущей стоимости" новой путевой точки не меньше, чем
            // значение "предыдущей стоимости" текущей путевой точки, возврат false.
            return false;
        }
        // Если в новой путевой точке еще нет открытой путевой точки
        // местоположения, добавляем новую путевую точку в коллекцию открытых путевых точек
        // и вернем true.
        open_waypoints.put(location, newWP);
        return true;
    }


    /** Возвращает текущее количество открытых путевых точек.**/
    public int numOpenWaypoints()
    {
        return open_waypoints.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте из открытого в список закрыто.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Возвращает значение true, если коллекция закрытых путевых точек содержит путевую точку
     * для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}