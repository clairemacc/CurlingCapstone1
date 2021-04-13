package services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 819466
 */
public class SortAndFilterService {

    public static List<String[]> contactSortandFilter(List<String[]> contacts, String sortNameBy, String positionFilter, String teamFilter) {

        String[] temp;
        int sortMetric;
        if (sortNameBy.equals("A-Z")) {
            for (int i = 0; i < contacts.size(); i++) {
                for (int j = 0; j < contacts.size(); j++) {
                    if (contacts.get(i)[0].compareTo(contacts.get(j)[0]) > 0) {
                        temp = contacts.get(i);
                        contacts.set(i, contacts.get(j));
                        contacts.set(j, temp);
                    }
                }
            }

        } else {
             for (int i = 0; i < contacts.size(); i++) {
                for (int j = 0; j < contacts.size(); j++) {
                    if (contacts.get(i)[0].toUpperCase().compareTo(contacts.get(j)[0].toUpperCase()) < 0) {
                        temp = contacts.get(i);
                        contacts.set(i, contacts.get(j));
                        contacts.set(j, temp);
                    }
                }
            }

        }

        return contacts;
    }
}