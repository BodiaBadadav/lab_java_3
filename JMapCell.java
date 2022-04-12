import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * Этот класс представляет собой пользовательский компонент
 * Swing для представления одной ячейки карты на 2D-карте.
 * Ячейка имеет несколько различных состояний,
 * но самое основное состояние — проходима ячейка или нет.
 */
public class JMapCell extends JComponent
{
    private static final Dimension CELL_SIZE = new Dimension(12, 12);

    /**
     * True указывает, что ячейка является конечной точкой,
     * начальной или конечной.
     * **/
    boolean endpoint = false;


    /**
     * True указывает, что ячейка проходима; ложь означает, что это не так.
     * **/
    boolean passable = true;

    /**
     *  True указывает, что эта ячейка является
     *  частью пути между началом и концом.
     **/
    boolean path = false;

    /**
     * Построить новую ячейку карты с заданной «проходимостью». Ввод
     * true означает, что ячейка проходима.
     **/
    public JMapCell(boolean pass)
    {
        // Установите предпочтительный размер ячейки, чтобы управлять начальным размером окна.
        setPreferredSize(CELL_SIZE);

        setPassable(pass);
    }

    /**
     * Создать новую ячейку карты, которая по умолчанию является проходимой.
     * **/
    public JMapCell()
    {
        // Вызовите другой конструктор, указав true для «проходимого».
        this(true);
    }

    /**
     * Помечает эту ячейку как начальную или конечную.
     * **/
    public void setEndpoint(boolean end)
    {
        endpoint = end;
        updateAppearance();
    }

    /**
     * Устанавливает эту ячейку как проходимую или непроходимую.
     * Ввод true помечает ячейку как проходимую;
     * ввод false помечает его как непроходимый.
     **/
    public void setPassable(boolean pass)
    {
        passable = pass;
        updateAppearance();
    }

    /**
     *  Возвращает true, если эта ячейка проходима,
     *  или false в противном случае.
     * **/
    public boolean isPassable()
    {
        return passable;
    }

    /**
     * Переключает текущее "проходимое" состояние ячейки карты.
     * **/
    public void togglePassable()
    {
        setPassable(!isPassable());
    }

    /**
     * Помечает эту ячейку как часть пути, обнаруженного алгоритмом A*.
     * **/
    public void setPath(boolean path)
    {
        this.path = path;
        updateAppearance();
    }

    /**
     * Этот вспомогательный метод обновляет цвет
     * фона в соответствии с текущим внутренним состоянием ячейки.
     **/
    private void updateAppearance()
    {
        if (passable)
        {
            // Проходимая ячейка. Укажите его состояние рамкой.
            setBackground(Color.WHITE);

            if (endpoint)
                setBackground(Color.CYAN);
            else if (path)
                setBackground(Color.GREEN);
        }
        else
        {
            //Непроходимая клетка. Сделать все красным.
            setBackground(Color.RED);
        }
    }

    /**
     * Реализация метода рисования для рисования цвета фона в ячейке карты.
     **/
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}