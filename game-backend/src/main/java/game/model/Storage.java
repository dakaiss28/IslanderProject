package game.model;

import game.serialization.OptionalAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Storage {
    File mapFile;
    private List<Map> maps;

    /** [R23_1_STORAGE]
     * store in a file the different Maps created
     */

    public Storage() {

        mapFile = new File("maps.json");

        try {
            maps = getMaps();
            if (maps.size() == 0) {
                try (FileWriter out = new FileWriter(mapFile)) {
                    out.write("[]");
                    out.flush();
                    maps = new ArrayList<>();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (maps.size() == 0) {
            try (FileWriter out = new FileWriter(mapFile)) {
                out.write("[]");
                out.flush();
                maps = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public List<Map> getMaps() throws IOException {
        return OptionalAdapter.fromJSONtoMaps(mapFile);

    }


    /**
     *
     * @param map the map to be put on the list of map
     *   the map file is completely changed everytime a map is added
     */

    public void addMap(final Map map) {
        final AtomicBoolean alreadyExists = new AtomicBoolean(false);

        maps
                .stream()
                .forEach(m -> {
                    if (m.getMapName().equals(map.getMapName())) {
                        alreadyExists.set(true);
                    }
                });

        if(!alreadyExists.get()) {
            maps.add(map);
        }

        try (FileWriter out = new FileWriter(mapFile)) {
            out.write(OptionalAdapter.fromListMapToJSON(maps));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap(final String mapName) {
        return maps
                .stream()
                .filter(m -> m.getMapName().equals(mapName))
                .findFirst()
                .orElse(null);
    }

    public File getMapFile() {
        return mapFile;
    }

    public void setMapFileandUpdateList(final File mapFile) {
        this.mapFile = mapFile;

        try {
            maps = getMaps();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
