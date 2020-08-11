import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * in charge of reading block definition file.
 */
public class BlocksDefinitionReader {
    private int defaultWidth;
    private int defaultHeight;
    private int defaultHitPoints;
    private String defaultFill = null;
    private Color defaultStroke = null;
    private Map<String, Integer> spacerWidth;
    private Map<String, BlockCreator> blockCreators;


    /**
     * Constructor.
     * initialize the maps.
     */
    public BlocksDefinitionReader() {
        this.spacerWidth = new TreeMap<>();
        this.blockCreators = new TreeMap<>();
    }

    /**
     * Analysis lines in the file.
     *
     * @param reader : block definition file.
     * @return block factory according to symbols.
     * @throws Exception problems in reading file or invalid file format.
     */
    public BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws Exception {
        BufferedReader br = (BufferedReader) reader;
        String line;
        try {
            while ((line = br.readLine()) != null) { //reading every line
                if (line.startsWith("default")) { //split default values.
                    this.getDefaultValues(line);
                }
                if (line.startsWith("bdef")) { //blocks definitions.
                    this.getBlockValues(line);

                }
                if (line.equals("") || line.startsWith("#")) { //ignore
                    continue;
                }
                if (line.startsWith("sdef")) { //spacers definitions
                    int firstColon = -1;
                    int secondColon = -1;
                    int flag = 0;
                    for (int j = 0; j < line.length(); j++) {
                        if (line.charAt(j) == ':' && flag == 0) {
                            flag = 1;
                            firstColon = j;
                        } else if (line.charAt(j) == ':' && flag == 1) {
                            secondColon = j;
                        }
                    }
                    //add to spacers map
                    try {
                        spacerWidth.put("" + line.charAt(firstColon + 1)
                                , Integer.parseInt(line.substring(secondColon + 1, line.length())));
                    } catch (Exception e) {
                        throw new Exception("invalid value of spacers width in block definition file");
                    }

                }

            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new Exception("closing block definition file failed !");
                }
            }
        }

        return new BlocksFromSymbolsFactory(spacerWidth, blockCreators);
    }

    /**
     * get blocks deafult values.
     *
     * @param s : line of default values.
     * @throws Exception invalid format.
     */
    private void getDefaultValues(String s) throws Exception {
        int flag = -1;
        List<Integer> spacesIndex = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) { //spaces in the line.
            if (s.charAt(i) == ' ') {
                spacesIndex.add(i);
            }
        }
        spacesIndex.add(s.length());
        if (s.contains("height")) { //default height value.
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("height")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("height") + "height".length() + 1, flag);
                this.defaultHeight = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid default high value!");
            }

        }
        if (s.contains("width")) { //default width value.
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("width")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("width") + "width".length() + 1, flag);
                this.defaultWidth = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid default width value.");
            }
        }
        if (s.contains("hit_points")) { //default hit points value.
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("hit_points")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("hit_points") + "hit_points".length() + 1, flag);
                this.defaultHitPoints = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid default high points value!");
            }
        }
        if (s.contains("fill:")) { //default fill value,
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("fill:")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("fill:") + "fill:".length() + 6, flag - 1);
                this.defaultFill = k;
            } catch (Exception e) {
                throw new Exception("invalid default fill value !");
            }

        }
        if (s.contains("stroke:")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("stroke:")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            String k = s.substring(s.indexOf("stroke:") + "stroke:".length(), flag);
            if (k.contains("color")) {
                try {
                    String colorString = s.substring(s.indexOf("stroke:") + "stroke:".length() + 6, flag - 1);
                    this.defaultStroke = ColorsParser.colorFromString(colorString);
                } catch (Exception e) {
                    throw new Exception("invalid default stroke value!");
                }
            }
        }
    }

    /**
     * split bdef lines.
     *
     * @param s : line.
     * @throws Exception invalid format.
     */
    private void getBlockValues(String s) throws Exception {
        int width = -1;
        int height = -1;
        int hits = -1;
        char symbol = ' ';
        Color stroke = null;
        Map<Integer, String> hashFill = new TreeMap<>();
        int flag = -1;
        List<Integer> spacesIndex = new ArrayList<>(); //spaces in the line
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                spacesIndex.add(i);
            }
        }
        spacesIndex.add(s.length());
        //get default values if not defined in this line.
        if (!s.contains("height")) {
            height = this.defaultHeight;
        }
        if (!s.contains("width")) {
            width = this.defaultWidth;
        }
        if (!s.contains("hit_points")) {
            hits = defaultHitPoints;
        }
        if (!s.contains("stroke")) {
            stroke = defaultStroke;
        }
        if (!s.contains("fill:")) {
            hashFill.put(0, defaultFill);
        }

        //gey block symbol
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
                symbol = s.charAt(i + 1);
                break;
            }
        }
        if (s.contains("width")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("width")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("width") + "width".length() + 1, flag);
                width = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid width value of block with symbol " + symbol);
            }
        }
        if (s.contains("height")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("height")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("height") + "height".length() + 1, flag);
                height = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid height value of block with symbol " + symbol);
            }
        }
        if (s.contains("hit_points")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("hit_points")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("hit_points") + "hit_points".length() + 1, flag);
                hits = Integer.parseInt(k);
            } catch (Exception e) {
                throw new Exception("invalid hit_points value of block with symbol " + symbol);
            }
        }
        if (s.contains("stroke:")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("stroke:")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            String k = s.substring(s.indexOf("stroke:") + "stroke:".length(), flag);
            if (k.contains("color")) {
                try {
                    String colorString = s.substring(s.indexOf("stroke:") + "stroke:".length() + 6, flag - 1);
                    stroke = ColorsParser.colorFromString(colorString);
                } catch (Exception e) {
                    throw new Exception("invalid stroke value of block with symbol " + symbol);
                }
            }
        }
        if (s.contains("fill:")) {
            for (int m = 0; m < spacesIndex.size(); m++) {
                if (spacesIndex.get(m) > s.indexOf("fill:")) {
                    flag = spacesIndex.get(m);
                    break;
                }
            }
            try {
                String k = s.substring(s.indexOf("fill:") + "fill:".length() + 6, flag - 1);
                hashFill.put(0, k);
            } catch (Exception e) {
                throw new Exception("invalid fill value of block with symbol " + symbol);
            }
        }
        if (s.contains("fill-")) {
            String newS = s + "";
            int k;
            while (true) {
                spacesIndex.clear();
                if (newS.contains("fill-")) {
                    k = Integer.parseInt(newS.charAt(newS.indexOf("fill-") + 5) + "");
                    for (int i = 0; i < newS.length(); i++) {
                        if (newS.charAt(i) == ' ') {
                            spacesIndex.add(i);
                        }
                    }
                    spacesIndex.add(newS.length());
                    for (int m = 0; m < spacesIndex.size(); m++) {
                        if (spacesIndex.get(m) > newS.indexOf("fill-")) {
                            flag = spacesIndex.get(m);
                            break;
                        }
                    }
                    try {
                        String status = newS.substring(newS.indexOf("fill-") + "fill-".length() + 8, flag - 1);
                        hashFill.put(k, status);
                    } catch (Exception e) {
                        throw new Exception("invalid fill-" + k + " value of block with symbol " + symbol);
                    }
                }
                try {
                    newS = newS.substring(newS.indexOf("fill-") + 6);
                } catch (Exception e) {
                    break;
                }
            }
        }
        BlocksCreatorObject bco = new BlocksCreatorObject(width, height, hits, stroke, hashFill);
        this.blockCreators.put(symbol + "", bco);
    }
}