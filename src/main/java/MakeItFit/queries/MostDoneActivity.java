package MakeItFit.queries;

import java.io.Serializable;
import java.util.List;

import MakeItFit.activities.Activity;
import MakeItFit.activities.types.*;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;

/**
 * The class MostDoneActivity represents the query that returns the most done activity.
 *
 * @author Afonso Santos (a104276), Hélder Gomes (a104100) and Pedro Pereira (a104082)
 * @version (11052024)
 */

public class MostDoneActivity implements Serializable {

    /**
     * Executes the query and returns the result.
     * @param userManager
     *
     * @return the most done activity
     */

    public String executeQuery(UserManager userManager) {
        List<User> list_users = userManager.getAllUsers();

        int[] count = { 0, 0, 0, 0 };

        for (User u : list_users) {
            for (Activity a : u.getListActivities()) {
                if (a instanceof DistanceWithAltimetry) {
                    count[0]++;
                } else if (a instanceof Distance) {
                    count[1]++;
                } else if (a instanceof RepetitionsWithWeights) {
                    count[2]++;
                    /* One of the branches is never triggered
                    } else if (a instanceof Repetitions) {
                     */
                } else {
                    count[3]++;
                }
            }
        }

        int    maxIndex = max(count);
        String mostDoneActivity;
        switch (maxIndex) {
            case 0:
                mostDoneActivity = "DistanceWithAltimetry";
                break;
            case 1:
                mostDoneActivity = "Distance";
                break;
            /* fails testExecuteQuerySingleUserReturnsRepetitions/RepetitionsWithWeights
            case 2:
                mostDoneActivity = "Repetitions";
                break;
            case 3:
                mostDoneActivity = "RepetitionsWithWeights";
                break;
            */
            case 2:
                mostDoneActivity = "RepetitionsWithWeights";
                break;
            default:
                mostDoneActivity = "Repetitions";
                break;
                /* Would never be reached (dead code)
                default:
                    mostDoneActivity = "No activities";
                    break;
                */
        };
        return mostDoneActivity;
    }

    /**
     * Returns the index of the maximum value in the array.
     * @param array
     *
     * @return the index of the maximum value in the array
     */
    private static int max(int[] array) {
        int maxIndex = 0;

        for (int j = 0; j < array.length; j++) {
            if (array[j] > array[maxIndex]) {
                maxIndex = j;
            }
        }
        return maxIndex;
    }
}
