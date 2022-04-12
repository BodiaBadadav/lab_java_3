/**
 * Этот класс представляет конкретное место на 2D-карте.
 * Координаты целочисленные значения.
 **/
public class Location
{
    /** X координата. **/
    public int xCoord;

    /** Y координата. **/
    public int yCoord;


    /** Создает новое местоположение с указанными целыми координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создаем новую локацию с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }


    public boolean equals(Object obj) {

        // Если объект Location?
        if (obj instanceof Location) {

            // Приведем другой объект к типу Location, затем сравниваем. Возвращаем true, если они равны.
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }

        // Если мы попали сюда, то они не равны.  Возвращаем false. 
        return false;
    }

    /** Предоставляет хэш-код для каждого местоположения. **/
    public int hashCode() {
        int result = 17; // Некоторое основное значение

        // Используем это простое значение для объединения
        result = 37 * result + xCoord;
        result = 37 * result + yCoord;
        return result;
    }
}