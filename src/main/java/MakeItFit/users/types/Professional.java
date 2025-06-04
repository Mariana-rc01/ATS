package MakeItFit.users.types;

import java.io.Serializable;
import java.util.*;

import MakeItFit.activities.Activity;
import MakeItFit.users.Gender;
import MakeItFit.users.User;

/**
 * The Professional class defines a subclass of the User class representing a professional athlete.
 *
 * @author  Afonso Santos (a104276), Hélder Gomes (a104100) and Pedro Pereira (a104082)
 * @version (11052024)
 */
public class Professional extends User implements Serializable {
    /**
     * The specialization options for a professional athlete.
     */
    private String specialization;

    private int frequency;

    /**
     * The parameterized constructor for Professional.
     * Initializes a Professional with the given parameters.
     *
     * @param name         the name of the professional
     * @param age          the age of the professional
     * @param gender       the gender of the professional
     * @param weight       the weight of the professional
     * @param height       the height of the professional
     * @param bpm          the baseline pulse of the professional
     * @param level        the experience level of the professional
     * @param address      the address of the professional
     * @param phone        the phone number of the professional
     * @param email        the email of the professional
     * @param frequency    the training frequency of the professional
     */
    public Professional(String name,
                        int    age,
                        Gender gender,
                        float  weight,
                        int    height,
                        int    bpm,
                        int    level,
                        String address,
                        String phone,
                        String email,
                        int    frequency) {

        // CHANGED: Added argument validation
        super(name, age, gender, weight, height, bpm, level, address, phone, email);
        this.specialization = "No specialization";
        this.setFrequency(frequency);
    }

    /**
     * The copy constructor for Professional.
     * Creates a new Professional by copying the fields of another Professional.
     *
     * @param p the Professional to copy
     */
    public Professional(Professional p) {
        super(p);
        this.specialization = p.specialization;
        this.frequency      = p.frequency;
    }

    /**
     * Gets the specialization of the professional.
     *
     * @return the specialization of the professional
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the specialization of the professional.
     *
     * @param specialization the new specialization to set
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the training frequency of the professional.
     *
     * @return the training frequency of the professional
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the training frequency of the professional.
     *
     * @param frequency the new training frequency to set
     */
    public void setFrequency(int frequency) {
        // CHANGED: Added argument validation
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency must be a non-negative integer");
        }

        this.frequency = frequency;
    }

    /**
     * Updates the specialization of the professional based on the most frequent activity.
     * If the professional has no activities, the specialization is set to "No specialization".
     */
    public void updateSpecialization() {
        if (!this.getListActivities().isEmpty()) {
            Map<String, Integer> activityCount = new HashMap<>();

            for (Activity activity : this.getListActivities()) {
                String activityType = activity.getClass().getSimpleName();
                activityCount.put(activityType, activityCount.getOrDefault(activityType, 0) + 1);
            }

            String mostFrequentActivity =
                Collections.max(activityCount.entrySet(), Map.Entry.comparingByValue()).getKey();

            this.setSpecialization(mostFrequentActivity);
        } else {
            // CHANGED: added this missing assignment
            this.setSpecialization("No specialization");
        }
    }

    /**
     * Clones the Professional object, creating a new instance with the same values.
     *
     * @return a cloned instance of the professional
     */
    @Override
    public Professional clone() {
        return new Professional(this);
    }

    // CHANGED: added equals method

    /**
     * Checks if the user is equal to another object.
     *
     * @param o The object to compare.
     * @return True if the user is equal to the object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;

        Professional u = (Professional) o;
        return (super.equals(o) && this.frequency == u.frequency &&
                this.specialization.equals(u.specialization));
    }

    /**
     * Returns a string representation of the professional.
     *
     * @return a string representation including the specialization and training frequency
     */
    public String toString() {
        return super.toString() + "Specialization: " + specialization +
            "\nFrequency: " + frequency + "\n====================\n";
    }
}
