/**
 * Level-set info.
 */
public class LevelSet {
    private String key;
    private String description;
    private String path;


    /**
     * @param desc : level set description.
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * @param k : key will be pressed to select this set.
     */
    public void setKey(String k) {
        this.key = k;
    }

    /**
     * @param levelSetPath : level set path.
     */
    public void setPath(String levelSetPath) {
        this.path = levelSetPath;
    }

    /**
     * @return key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return level set description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return level set path.
     */
    public String getPath() {
        return this.path;
    }
}
