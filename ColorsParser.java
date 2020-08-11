import java.awt.Color;

/**
 * Color parser class.
 * reading a string and return the appropriate color.
 */
public class ColorsParser {

    /**
     * reading color from a given string.
     *
     * @param s : string define color name or RGB format.
     * @return appropriate color according to the string.
     */
    public static Color colorFromString(String s) {
        //string define color name
        if (s.equals("black")) {
            return Color.BLACK;
        }
        if (s.equals("blue")) {
            return Color.blue;
        }
        if (s.equals("cyan")) {
            return Color.cyan;
        }
        if (s.equals("gray")) {
            return Color.gray;
        }
        if (s.equals("lightGray")) {
            return Color.lightGray;
        }
        if (s.equals("green")) {
            return Color.green;
        }
        if (s.equals("orange")) {
            return Color.orange;
        }
        if (s.equals("pink")) {
            return Color.PINK;
        }
        if (s.equals("red")) {
            return Color.red;
        }
        if (s.equals("white")) {
            return Color.white;
        }
        if (s.equals("yellow")) {
            return Color.yellow;
        }
        if (s.equals("darkGray")) {
            return Color.darkGray;
        }

        //string define RGB format of specific color.
        if (s.startsWith("RGB")) {
            String degrees = s.substring(4, s.length() - 1);
            int firstComma = -1;
            int secondComma = -1;
            int flag = 0;
            for (int i = 0; i < degrees.length(); i++) {
                if (degrees.charAt(i) == ',' && flag == 0) {
                    firstComma = i;
                    flag = 1;
                }
                if (degrees.charAt(i) == ',' && flag == 1) {
                    secondComma = i;
                }
            }
            //RGB components.
            int x = Integer.parseInt(degrees.substring(0, firstComma));
            int y = Integer.parseInt(degrees.substring(firstComma + 1, secondComma));
            int z = Integer.parseInt(degrees.substring(secondComma + 1, degrees.length()));
            return new Color(x, y, z);
        }

        //string does not define a color.
        return null;
    }
}