package figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Caontains all figures that were created in the setup phase of the game.
 * @author ulprv
 */
public class FigureRepository {
    private final List<Figure> figures;

    /**
     * Creates the repository of figures.
     */
    public FigureRepository() {
        this.figures = new ArrayList<>();
    }

    /**
     * Ensures Game integrity by not allowing to figures with the same name. Instead, updates a figures data.
     *
     * @param name of the figure
     * @param fd stats of the figure
     */
    public void addFigure(String name, FigureData fd) {
        Figure f = this.getFigureByName(name);

        if (f == null) {
            figures.add(new Figure(name, fd));
            IO.println("Fighter " + name + " created");
        } else {
            f.getStatManager().setData(fd);
            IO.println("Fighter " + name + " updated");
        }
    }

    /**
     * gets a figure by name.
     *
     * @param name of a figure
     * @return figure
     */
    public Figure getFigureByName(String name) {
        return figures.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .findFirst()
                .orElse(null);
    }

    /**
     * returns list of all figures created.
     *
     * @return list of figures
     */
    public List<Figure> getAllFigures() {
        return this.figures;
    }


}
