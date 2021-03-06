package org.alignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tgiunipero
 */
public class AlignmentFile implements Serializable {

    private static final long serialVersionUID = 7680645765319662728L;

    private List<String> entity1Array;
    private List<String> entity2Array;
    private List<String> matchings;
    private String relation;

    // class-wide containers for entity URIs of all loaded files
    private static List<String> Entity1Pool = new ArrayList<String>();
    private static List<String> Entity2Pool = new ArrayList<String>();


    // constructor
    public AlignmentFile() {
        entity1Array = new ArrayList<String>();
        entity2Array = new ArrayList<String>();
        matchings = new ArrayList<String>();
    }

    public List<String> getEntity1Array() {
        return entity1Array;
    }

    public List<String> getEntity2Array() {
        return entity2Array;
    }

    public List<String> getMatchings() {
        return matchings;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    /* Returns matchings in a format that is JSON-compliant
     *
     * The int parameter is used to specify an abridged
     * version of the measure values - it represents the
     * number of significant digits included in the
     * measure - useful for restricting the data size.
     *
     * A stringified matching will include the values for
     * entities 1 and 2, and the confidence score:
     *
     *      {"e1":2,"e2":5,"c":0.708}
     */
    public String getMatchingsAsString(int i) {

        i = i+2;    // shift 2 chars to account for '0.'

        String data = "";
        Iterator<String> iter = getMatchings().iterator();

        while (iter.hasNext()) {
            String matching = iter.next();
            String[] tokens = matching.split(",");
            String confScore = tokens[2];

            // abridge matching
            if (confScore.length() > i) {
                confScore = confScore.substring(0, i-1);
            }

            data += "{\"e1\":" + tokens[0] +   // entity 1
                    ",\"e2\":" + tokens[1] +   // entity 2
                    ",\"c\":"  + confScore +   // confidence score
                    "},";
        }

        // remove final ','
        data = data.substring(0, data.length()-1);

        return data;
    }

    public static List<String> getEntity1Pool() {
        return Entity1Pool;
    }

    public static List<String> getEntity2Pool() {
        return Entity2Pool;
    }
}