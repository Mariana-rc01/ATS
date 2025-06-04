package MakeItFit;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

import MakeItFit.MakeItFit;
import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.*;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.activities.implementation.WeightSquat;
import MakeItFit.exceptions.*;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.types.Amateur;
import MakeItFit.users.types.Occasional;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class MakeItFitTest {
    @Test
    void testCreateAndGetUser() {
        MakeItFit model = new MakeItFit();
        model.createUser("Mariana Pereira",
                         44,
                         Gender.Other,
                         60,
                         186,
                         83,
                         8,
                         "Rua Adriana Campos 70, Porto",
                         "215701622",
                         "j4jt3fhu3ylx@gmail.com",
                         5,
                         "Occasional");
        assertTrue(model.existsUserWithEmail("j4jt3fhu3ylx@gmail.com"));
        User user = model.getUser("j4jt3fhu3ylx@gmail.com");
        assertEquals("Mariana Pereira", user.getName());
        assertEquals(44, user.getAge());
    }

    @Test
    void testRemoveUserByEmail() {
        MakeItFit model = new MakeItFit();
        model.createUser("Filipe Coelho",
                         68,
                         Gender.Male,
                         52,
                         156,
                         60,
                         4,
                         "Rua Mariana Rocha 40, 8\u00ba esq., Lisboa",
                         "277882553",
                         "9tn0cev8naqf1wd7@hotmail.com",
                         12,
                         "Occasional");
        model.removeUser("9tn0cev8naqf1wd7@hotmail.com");
        assertFalse(model.existsUserWithEmail("9tn0cev8naqf1wd7@hotmail.com"));
    }

    @Test
    void testRemoveUserByUUID() {
        MakeItFit model = new MakeItFit();
        model.createUser("Mariana Rocha",
                         49,
                         Gender.Male,
                         100,
                         183,
                         96,
                         9,
                         "Rua Carlos Campos 47, 9\u00ba esq., Porto",
                         "276734334",
                         "ojh@hotmail.com",
                         0,
                         "Amateur");
        UUID id = model.getUser("ojh@hotmail.com").getCode();
        model.removeUser(id);
        assertFalse(model.existsUserWithEmail("ojh@hotmail.com"));
    }

    @Test
    void testRemoveUserInvalidType() {
        MakeItFit model = new MakeItFit();
        assertThrows(InvalidTypeException.class, () -> model.removeUser(123));
    }

    @Test
    void testGetUserInvalidType() {
        MakeItFit model = new MakeItFit();
        assertThrows(InvalidTypeException.class, () -> model.getUser(123));
    }

    @Test
    void testRemoveNonExistentUser() {
        MakeItFit model = new MakeItFit();
        assertThrows(EntityDoesNotExistException.class,
                     () -> model.removeUser("mqxt9v0hbn5ahni9@gmail.com"));
    }

    @Test
    void testUpdateUser() {
        MakeItFit model = new MakeItFit();
        model.createUser("Margarida Gon\u00e7alves",
                         75,
                         Gender.Other,
                         91,
                         168,
                         93,
                         9,
                         "Rua Dinis Silva 30, Porto",
                         "913876402",
                         "qmntssg45pet6yvinonk2@gmail.com",
                         0,
                         "Amateur");
        model.updateUserName("Eva Lopes", "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserAge(76, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserGender(Gender.Male, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserWeight(89f, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserHeight(178, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserBpm(77, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserLevel(1, "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserAddress("Rua Humberto Gomes 81, 6\u00ba dto., Porto",
                                "qmntssg45pet6yvinonk2@gmail.com");
        model.updateUserPhone("260895464", "qmntssg45pet6yvinonk2@gmail.com");
        User updated = model.getUser("qmntssg45pet6yvinonk2@gmail.com");
        UUID id      = updated.getCode();
        User u       = model.getUser(id);
        assertEquals("Eva Lopes", u.getName());
        assertEquals("Eva Lopes", updated.getName());
        assertEquals(76, updated.getAge());
        assertEquals(Gender.Male, updated.getGender());
        assertEquals(89f, updated.getWeight());
        assertEquals(178, updated.getHeight());
        assertEquals(77, updated.getBpm());
        assertEquals(1, updated.getLevel());
        assertEquals("Rua Humberto Gomes 81, 6\u00ba dto., Porto", updated.getAddress());
        assertEquals("260895464", updated.getPhone());
    }

    @Test
    void testUpdateEmail() {
        MakeItFit model = new MakeItFit();
        model.createUser("Bruno Dias",
                         35,
                         Gender.Male,
                         71,
                         179,
                         73,
                         4,
                         "Rua Adriana Rocha 94, 1\u00ba esq., Lisboa",
                         "213714487",
                         "t77ugu2c3m@aol.com",
                         5,
                         "Occasional");
        model.updateUserEmail("t77ugu2c3m@aol.com", "xxqgw1p0i27tbwylc79pf2enm6j2q@hotmail.com");
        assertFalse(model.existsUserWithEmail("t77ugu2c3m@aol.com"));
        assertTrue(model.existsUserWithEmail("xxqgw1p0i27tbwylc79pf2enm6j2q@hotmail.com"));
        User user = model.getUser("xxqgw1p0i27tbwylc79pf2enm6j2q@hotmail.com");
        assertEquals("Bruno Dias", user.getName());
    }

    @Test
    void testDuplicateUser() {
        MakeItFit model = new MakeItFit();
        model.createUser("Beatriz Pereira",
                         50,
                         Gender.Other,
                         86,
                         181,
                         66,
                         9,
                         "Rua Bruno Gomes 42, Braga",
                         "254120248",
                         "kukm@gmail.com",
                         11,
                         "Professional");
        assertThrows(ExistingEntityConflictException.class,
                     ()
                         -> model.createUser("Beatriz Pereira",
                                             50,
                                             Gender.Other,
                                             86,
                                             181,
                                             66,
                                             9,
                                             "Rua Bruno Gomes 42, Braga",
                                             "254120248",
                                             "kukm@gmail.com",
                                             11,
                                             "Professional"));
    }

    @Test
    void testGetAllUsers() {
        MakeItFit  model    = new MakeItFit();
        List<User> allUsers = model.getAllUsers();
        assertNotNull(allUsers);
        assertTrue(allUsers.isEmpty());
        model.createUser("Jos\u00e9 Gon\u00e7alves",
                         40,
                         Gender.Male,
                         54,
                         181,
                         97,
                         1,
                         "Rua Eva Pereira 13, 4\u00ba esq., Lisboa",
                         "935973304",
                         "f@gmail.com",
                         0,
                         "Amateur");
        allUsers            = model.getAllUsers();
        List<String> emails = allUsers.stream().map(User::getEmail).collect(Collectors.toList());
        assertTrue(emails.contains("f@gmail.com"));
    }

    @Test
    void getActivitiesFromUserTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Humberto Rocha",
                         55,
                         Gender.Male,
                         87,
                         169,
                         71,
                         6,
                         "Rua Beatriz Gomes 22, 10\u00ba dto., Lisboa",
                         "241475844",
                         "u4ui0d89yfw00guukxqk0m0syhtql4@hotmail.com",
                         5,
                         "Occasional");
        UUID userCode = model.getUser("u4ui0d89yfw00guukxqk0m0syhtql4@hotmail.com").getCode();
        List<Activity> activities = Arrays.asList(
            new Trail(userCode,
                      MakeItFitDate.of(2009, 5, 14),
                      60,
                      "Treino das 19h",
                      "Body Pump",
                      27.370168215992315,
                      3.9948316962700097,
                      7.17058669051176,
                      Trail.TRAIL_TYPE_HARD),
            new PushUp(userCode,
                       MakeItFitDate.of(2009, 1, 9),
                       30,
                       "Treino do 12h",
                       "Corrida",
                       59,
                       1),
            new PushUp(userCode,
                       MakeItFitDate.of(2007, 9, 11),
                       60,
                       "Treino para emagrecer",
                       "Yoga",
                       6,
                       4),
            new PushUp(userCode,
                       MakeItFitDate.of(2012, 5, 27),
                       30,
                       "Treino das 9h",
                       "Corrida",
                       20,
                       5),
            new PushUp(userCode,
                       MakeItFitDate.of(2004, 4, 7),
                       45,
                       "Treino das 9h",
                       "Nata\u00e7\u00e3o",
                       49,
                       6),
            new Running(userCode,
                        MakeItFitDate.of(2023, 7, 12),
                        45,
                        "Treino das 17h",
                        "Cycling",
                        4.340112396588658,
                        10.33507464310139),
            new Trail(userCode,
                      MakeItFitDate.of(2003, 12, 11),
                      10,
                      "Treino das 15h",
                      "Boxe",
                      37.49845442131308,
                      12.319380211153337,
                      7.753634846073059,
                      Trail.TRAIL_TYPE_EASY),
            new WeightSquat(userCode,
                            MakeItFitDate.of(2021, 12, 26),
                            20,
                            "Treino das 15h",
                            "Caminhada",
                            53,
                            6,
                            11.68579614049322),
            new Running(userCode,
                        MakeItFitDate.of(2012, 5, 21),
                        20,
                        "Treino das 15h",
                        "Pilates",
                        16.949792464504917,
                        11.077716857440008),
            new PushUp(userCode,
                       MakeItFitDate.of(2015, 6, 20),
                       20,
                       "Treino das 9h",
                       "Nata\u00e7\u00e3o",
                       41,
                       3),
            new WeightSquat(userCode,
                            MakeItFitDate.of(2014, 6, 22),
                            15,
                            "Passeio",
                            "Caminhada",
                            73,
                            7,
                            16.238947761392865),
            new Trail(userCode,
                      MakeItFitDate.of(2009, 10, 18),
                      45,
                      "Treino das 15h",
                      "Body Pump",
                      28.29630056902144,
                      11.581312512717226,
                      2.653018536766483,
                      Trail.TRAIL_TYPE_MEDIUM),
            new Running(userCode,
                        MakeItFitDate.of(2002, 11, 13),
                        30,
                        "Treino das 19h",
                        "Boxe",
                        2.7517923438151475,
                        10.445036609316713),
            new Running(userCode,
                        MakeItFitDate.of(2008, 5, 26),
                        15,
                        "Treino das 15h",
                        "Yoga",
                        17.958705374736752,
                        12.513454520334825),
            new Trail(userCode,
                      MakeItFitDate.of(2012, 10, 30),
                      20,
                      "Treino das 19h",
                      "Boxe",
                      31.451415228606486,
                      7.137772936685614,
                      11.482466535841109,
                      Trail.TRAIL_TYPE_HARD),
            new WeightSquat(userCode,
                            MakeItFitDate.of(2013, 9, 8),
                            10,
                            "Treino das 15h",
                            "Bicicleta",
                            73,
                            10,
                            15.812156858358854),
            new PushUp(userCode, MakeItFitDate.of(2024, 5, 14), 30, "Treino do 12h", "Yoga", 94, 6),
            new PushUp(userCode, MakeItFitDate.of(2016, 2, 1), 15, "Passeio", "Boxe", 59, 8),
            new Running(userCode,
                        MakeItFitDate.of(2006, 10, 17),
                        30,
                        "Treino das 19h",
                        "Muscula\u00e7\u00e3o",
                        16.43168394846358,
                        10.884338727545156),
            new Trail(userCode,
                      MakeItFitDate.of(2017, 6, 13),
                      30,
                      "Treino das 19h",
                      "Body Pump",
                      7.901801220722576,
                      7.676413878342446,
                      15.993265538308442,
                      Trail.TRAIL_TYPE_MEDIUM),
            new Running(userCode,
                        MakeItFitDate.of(2015, 4, 20),
                        10,
                        "Treino das 19h",
                        "Nata\u00e7\u00e3o",
                        10.002229217877492,
                        13.331162855737643),
            new Running(userCode,
                        MakeItFitDate.of(2010, 11, 24),
                        30,
                        "Treino das 17h",
                        "Bicicleta",
                        17.641618955627095,
                        8.711895392069744),
            new PushUp(userCode,
                       MakeItFitDate.of(2017, 11, 18),
                       10,
                       "Treino das 19h",
                       "Boxe",
                       91,
                       8),
            new PushUp(userCode,
                       MakeItFitDate.of(2015, 5, 31),
                       30,
                       "Treino das 17h",
                       "Caminhada",
                       28,
                       8),
            new WeightSquat(userCode,
                            MakeItFitDate.of(2016, 3, 29),
                            45,
                            "Treino das 19h",
                            "Muscula\u00e7\u00e3o",
                            18,
                            2,
                            11.023234265349998),
            new Trail(userCode,
                      MakeItFitDate.of(2024, 2, 3),
                      20,
                      "Treino das 17h",
                      "Bicicleta",
                      27.74846038557333,
                      18.731879485529177,
                      17.034142965708984,
                      Trail.TRAIL_TYPE_MEDIUM),
            new Running(userCode,
                        MakeItFitDate.of(2025, 11, 18),
                        20,
                        "Treino das 19h",
                        "Zumba",
                        3.0109458429660765,
                        13.057528822433428));
        for (Activity a : activities)
            model.addActivityToUser("u4ui0d89yfw00guukxqk0m0syhtql4@hotmail.com", a);
        assertEquals(
            model.getActivitiesFromUser("u4ui0d89yfw00guukxqk0m0syhtql4@hotmail.com").size(),
            27);
    }

    @Test
    void addActivityToUserTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Sofia Silva",
                         30,
                         Gender.Male,
                         90,
                         162,
                         67,
                         10,
                         "Rua Eva Campos 89, 4\u00ba esq., Braga",
                         "925057415",
                         "2ai7tb@gmail.com",
                         8,
                         "Professional");
        ;
        UUID           userCode   = model.getUser("2ai7tb@gmail.com").getCode();
        List<Activity> activities = Arrays.asList(new PushUp(userCode,
                                                             MakeItFitDate.of(2015, 9, 29),
                                                             30,
                                                             "Treino das 19h",
                                                             "Body Pump",
                                                             42,
                                                             2),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2016, 9, 8),
                                                            20,
                                                            "Treino das 9h",
                                                            "Pilates",
                                                            15.475726161411952,
                                                            9.96556644289559,
                                                            16.304354085923976,
                                                            Trail.TRAIL_TYPE_HARD),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2023, 2, 11),
                                                              45,
                                                              "Treino das 15h",
                                                              "Muscula\u00e7\u00e3o",
                                                              11.843980878329278,
                                                              6.379632166917055),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2021, 6, 14),
                                                             10,
                                                             "Treino das 9h",
                                                             "Trail",
                                                             25,
                                                             2),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2009, 7, 17),
                                                            30,
                                                            "Passeio",
                                                            "Trail",
                                                            21.367011315377656,
                                                            13.395490207682222,
                                                            2.0821202178426317,
                                                            Trail.TRAIL_TYPE_MEDIUM),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2004, 1, 5),
                                                              20,
                                                              "Treino do 12h",
                                                              "Pilates",
                                                              4.7549796284869315,
                                                              8.708942712864523),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2021, 9, 19),
                                                              10,
                                                              "Treino para emagrecer",
                                                              "Nata\u00e7\u00e3o",
                                                              7.652655945329599,
                                                              14.954361414130025),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2017, 7, 25),
                                                             30,
                                                             "Treino das 19h",
                                                             "Trail",
                                                             70,
                                                             5),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2025, 4, 22),
                                                            30,
                                                            "Treino das 17h",
                                                            "Nata\u00e7\u00e3o",
                                                            2.8134624404845976,
                                                            17.818884058005445,
                                                            12.814694972960966,
                                                            Trail.TRAIL_TYPE_EASY),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2015, 3, 25),
                                                             15,
                                                             "Treino das 15h",
                                                             "Zumba",
                                                             50,
                                                             2),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2025, 6, 19),
                                                             20,
                                                             "Treino das 19h",
                                                             "Muscula\u00e7\u00e3o",
                                                             100,
                                                             2),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2017, 8, 17),
                                                             15,
                                                             "Treino das 19h",
                                                             "Pilates",
                                                             13,
                                                             6),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2001, 9, 24),
                                                              30,
                                                              "Treino das 19h",
                                                              "Muscula\u00e7\u00e3o",
                                                              19.781409188131587,
                                                              11.010316407701957),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2023, 12, 19),
                                                             15,
                                                             "Treino das 19h",
                                                             "Caminhada",
                                                             37,
                                                             5),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2010, 4, 10),
                                                            10,
                                                            "Treino das 9h",
                                                            "Nata\u00e7\u00e3o",
                                                            41.17161510828845,
                                                            8.890090408115956,
                                                            3.6048015082483964,
                                                            Trail.TRAIL_TYPE_HARD),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2014, 11, 23),
                                                            60,
                                                            "Treino das 9h",
                                                            "Corrida",
                                                            8.072824416254386,
                                                            8.84896146051014,
                                                            15.20070869301945,
                                                            Trail.TRAIL_TYPE_HARD),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2011, 12, 19),
                                                                  60,
                                                                  "Treino das 15h",
                                                                  "Corrida",
                                                                  90,
                                                                  4,
                                                                  11.235436092281654),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2007, 4, 25),
                                                             10,
                                                             "Treino das 19h",
                                                             "Trail",
                                                             88,
                                                             6),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2016, 5, 8),
                                                            10,
                                                            "Treino para emagrecer",
                                                            "Pilates",
                                                            3.307326578286607,
                                                            10.569114849013744,
                                                            1.9405293961281014,
                                                            Trail.TRAIL_TYPE_EASY),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2013, 9, 8),
                                                            15,
                                                            "Treino das 17h",
                                                            "Pilates",
                                                            6.835210917431921,
                                                            13.616775973137136,
                                                            18.800033257440283,
                                                            Trail.TRAIL_TYPE_EASY),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2006, 3, 25),
                                                                  15,
                                                                  "Treino para emagrecer",
                                                                  "Muscula\u00e7\u00e3o",
                                                                  56,
                                                                  7,
                                                                  14.136069682671163),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2008, 12, 15),
                                                             15,
                                                             "Treino das 15h",
                                                             "Yoga",
                                                             84,
                                                             8),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2024, 11, 25),
                                                            60,
                                                            "Treino para emagrecer",
                                                            "Cycling",
                                                            6.949569480289036,
                                                            5.639995034944204,
                                                            9.057954500898218,
                                                            Trail.TRAIL_TYPE_MEDIUM),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2020, 9, 18),
                                                            15,
                                                            "Treino das 19h",
                                                            "Pilates",
                                                            20.24166842274786,
                                                            8.80278792434624,
                                                            6.603875170551196,
                                                            Trail.TRAIL_TYPE_HARD),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2017, 6, 6),
                                                              15,
                                                              "Treino das 19h",
                                                              "Pilates",
                                                              4.9445111025236805,
                                                              7.569031757584559));
        for (Activity a : activities)
            model.addActivityToUser("2ai7tb@gmail.com", a);
        assertEquals(model.getActivitiesFromUser("2ai7tb@gmail.com").size(), 25);
        model.addActivityToUser("2ai7tb@gmail.com",
                                new WeightSquat(userCode,
                                                MakeItFitDate.of(2005, 9, 26),
                                                60,
                                                "Treino das 17h",
                                                "Caminhada",
                                                77,
                                                5,
                                                17.009419109248196));
        assertEquals(model.getActivitiesFromUser("2ai7tb@gmail.com").size(), 26);
    }

    @Test
    void removeActivityFromUserTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Ol\u00edvia Silva",
                         55,
                         Gender.Female,
                         75,
                         184,
                         85,
                         10,
                         "Rua Andr\u00e9 Gomes 78, Braga",
                         "929516673",
                         "6hmh8u85brferx3j4b5dsyd@yahoo.com",
                         1,
                         "Occasional");
        UUID           userCode   = model.getUser("6hmh8u85brferx3j4b5dsyd@yahoo.com").getCode();
        List<Activity> activities = Arrays.asList(new PushUp(userCode,
                                                             MakeItFitDate.of(2003, 3, 30),
                                                             60,
                                                             "Treino das 9h",
                                                             "Caminhada",
                                                             43,
                                                             3),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2006, 4, 6),
                                                             10,
                                                             "Treino das 17h",
                                                             "Caminhada",
                                                             94,
                                                             8),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2009, 9, 25),
                                                             10,
                                                             "Treino das 17h",
                                                             "Pilates",
                                                             6,
                                                             2),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2023, 2, 20),
                                                            45,
                                                            "Passeio",
                                                            "Zumba",
                                                            43.38017695529357,
                                                            10.105217668910653,
                                                            0.16911187961234664,
                                                            Trail.TRAIL_TYPE_MEDIUM),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2012, 2, 21),
                                                                  45,
                                                                  "Treino para emagrecer",
                                                                  "Corrida",
                                                                  80,
                                                                  4,
                                                                  17.10434105942477),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2025, 9, 16),
                                                              15,
                                                              "Treino das 9h",
                                                              "Cycling",
                                                              4.995695234850475,
                                                              10.224539821217672),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2010, 9, 2),
                                                              15,
                                                              "Treino das 9h",
                                                              "Trail",
                                                              5.800488107626588,
                                                              6.229752377619365),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2015, 2, 5),
                                                                  20,
                                                                  "Treino para emagrecer",
                                                                  "Pilates",
                                                                  89,
                                                                  9,
                                                                  11.559882425402435),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2004, 2, 1),
                                                                  45,
                                                                  "Treino do 12h",
                                                                  "Bicicleta",
                                                                  70,
                                                                  6,
                                                                  15.034408137349702),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2017, 7, 23),
                                                            15,
                                                            "Treino das 19h",
                                                            "Cycling",
                                                            28.21618638216132,
                                                            7.1121554117092955,
                                                            3.297993685409932,
                                                            Trail.TRAIL_TYPE_EASY),
                                                  new WeightSquat(userCode,
                                                                  MakeItFitDate.of(2016, 1, 23),
                                                                  15,
                                                                  "Passeio",
                                                                  "Corrida",
                                                                  75,
                                                                  7,
                                                                  19.775450045899255),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2025, 2, 1),
                                                            45,
                                                            "Treino do 12h",
                                                            "Boxe",
                                                            42.490529176493126,
                                                            9.677930621923235,
                                                            11.721369034320833,
                                                            Trail.TRAIL_TYPE_MEDIUM),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2002, 8, 12),
                                                              30,
                                                              "Treino do 12h",
                                                              "Caminhada",
                                                              10.847474893778232,
                                                              14.063076231892282),
                                                  new PushUp(userCode,
                                                             MakeItFitDate.of(2014, 10, 27),
                                                             60,
                                                             "Treino das 17h",
                                                             "Pilates",
                                                             54,
                                                             1),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2024, 5, 4),
                                                              15,
                                                              "Treino para emagrecer",
                                                              "Bicicleta",
                                                              2.8066327040543735,
                                                              8.884556400136137),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2017, 11, 28),
                                                            15,
                                                            "Treino para emagrecer",
                                                            "Boxe",
                                                            13.203463650868215,
                                                            3.6853988600775334,
                                                            6.939203080728008,
                                                            Trail.TRAIL_TYPE_EASY),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2020, 3, 31),
                                                              30,
                                                              "Treino das 9h",
                                                              "Yoga",
                                                              6.539338265200139,
                                                              13.942236088534033),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2014, 6, 27),
                                                              60,
                                                              "Treino das 15h",
                                                              "Bicicleta",
                                                              4.420399785955877,
                                                              12.729748315674696),
                                                  new Running(userCode,
                                                              MakeItFitDate.of(2006, 10, 11),
                                                              60,
                                                              "Passeio",
                                                              "Pilates",
                                                              1.634384419203105,
                                                              12.910608816131566),
                                                  new Trail(userCode,
                                                            MakeItFitDate.of(2019, 6, 6),
                                                            15,
                                                            "Treino para emagrecer",
                                                            "Body Pump",
                                                            32.74837288640716,
                                                            3.7464237086913292,
                                                            13.71669593265592,
                                                            Trail.TRAIL_TYPE_MEDIUM));
        for (Activity a : activities)
            model.addActivityToUser("6hmh8u85brferx3j4b5dsyd@yahoo.com", a);
        List<UUID> activityIds = new java.util.ArrayList<>();
        for (Activity a : activities)
            activityIds.add(a.getCode());
        for (UUID activityId : activityIds)
            model.removeActivityFromUser("6hmh8u85brferx3j4b5dsyd@yahoo.com", activityId);
        assertEquals(model.getActivitiesFromUser("6hmh8u85brferx3j4b5dsyd@yahoo.com").size(), 0);
    }

    @Test
    void createTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Carlota Silva",
                         77,
                         Gender.Male,
                         75,
                         190,
                         72,
                         10,
                         "Rua Hugo Campos 52, 2\u00ba esq., Porto",
                         "279933561",
                         "t26yvcxbp6o71pz4u4nlpyhvt3qn@yahoo.com",
                         14,
                         "Professional");
        UUID          userCode  = model.getUser("t26yvcxbp6o71pz4u4nlpyhvt3qn@yahoo.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2016, 3, 15);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        assertNotNull(planCode);
    }

    @Test
    void createTrainingPlanExceptionTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Sofia Gomes",
                         55,
                         Gender.Other,
                         88,
                         191,
                         98,
                         7,
                         "Rua Dalila Dias 24, Coimbra",
                         "286692707",
                         "81r1dt55g1ogkxhr@gmail.com",
                         13,
                         "Occasional");
        UUID userCode = model.getUser("81r1dt55g1ogkxhr@gmail.com").getCode();
        assertThrows(IllegalArgumentException.class,
                     () -> { model.createTrainingPlan(userCode, null); });
    }

    @Test
    void constructTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Beatriz Dias",
                         44,
                         Gender.Male,
                         86,
                         190,
                         92,
                         10,
                         "Rua Henrique Coelho 87, 5\u00ba esq., Coimbra",
                         "208575698",
                         "ekmfleesiik@yahoo.com",
                         4,
                         "Occasional");
        UUID          userCode  = model.getUser("ekmfleesiik@yahoo.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2020, 8, 20);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        TrainingPlan  plan      = model.getTrainingPlan(planCode);
        assertDoesNotThrow(
            () -> { model.constructTrainingPlanByObjectives(plan, true, 2, 3, 4, 500); });
    }

    @Test
    void constructTrainingPlanExceptionTest() {
        MakeItFit     model        = new MakeItFit();
        UUID          fakeUserCode = UUID.randomUUID();
        MakeItFitDate startDate    = MakeItFitDate.of(2018, 10, 22);
        TrainingPlan  fakePlan     = new TrainingPlan(fakeUserCode, startDate);
        assertThrows(EntityDoesNotExistException.class, () -> {
            model.constructTrainingPlanByObjectives(fakePlan, true, 2, 3, 4, 500);
        });
    }

    @Test
    void removeTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Carlota Campos",
                         68,
                         Gender.Female,
                         63,
                         167,
                         96,
                         4,
                         "Rua Mariana Lopes 41, Porto",
                         "293206625",
                         "fybccbn2i8@hotmail.com",
                         12,
                         "Occasional");
        UUID          userCode  = model.getUser("fybccbn2i8@hotmail.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2001, 12, 23);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        model.removeTrainingPlan(planCode);
        assertThrows(EntityDoesNotExistException.class, () -> { model.getTrainingPlan(planCode); });
    }

    @Test
    void getTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Margarida Rocha",
                         79,
                         Gender.Female,
                         57,
                         190,
                         68,
                         6,
                         "Rua Dinis Campos 85, 6\u00ba esq., Porto",
                         "207754501",
                         "sl6u48hu7dres8a8wh55g4n5lyx@hotmail.com",
                         7,
                         "Professional");
        UUID          userCode = model.getUser("sl6u48hu7dres8a8wh55g4n5lyx@hotmail.com").getCode();
        MakeItFitDate startDate     = MakeItFitDate.of(2004, 6, 9);
        UUID          planCode      = model.createTrainingPlan(userCode, startDate);
        TrainingPlan  retrievedPlan = model.getTrainingPlan(planCode);
        assertNotNull(retrievedPlan);
        assertEquals(planCode, retrievedPlan.getCode());
    }

    @Test
    void getTrainingPlanExceptionTest() {
        MakeItFit model       = new MakeItFit();
        UUID      invalidCode = UUID.randomUUID();
        assertThrows(EntityDoesNotExistException.class,
                     () -> { model.getTrainingPlan(invalidCode); });
    }

    @Test
    void updateTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Mariana Pereira",
                         69,
                         Gender.Female,
                         80,
                         151,
                         82,
                         9,
                         "Rua Dinis Gon\u00e7alves 99, 9\u00ba dto., Lisboa",
                         "276368306",
                         "q6matseh@hotmail.com",
                         2,
                         "Occasional");
        UUID          userCode  = model.getUser("q6matseh@hotmail.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2001, 10, 25);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        TrainingPlan  plan      = model.getTrainingPlan(planCode);
        plan.setStartDate(MakeItFitDate.of(2010, 5, 13));
        assertDoesNotThrow(() -> model.updateTrainingPlan(plan));
        TrainingPlan updated = model.getTrainingPlan(planCode);
        assertEquals(MakeItFitDate.of(2010, 5, 13), updated.getStartDate());
    }

    @Test
    void updateTrainingPlanExceptionTest() {
        MakeItFit     model     = new MakeItFit();
        MakeItFitDate startDate = MakeItFitDate.of(2012, 6, 21);
        TrainingPlan  fakePlan  = new TrainingPlan(UUID.randomUUID(), startDate);
        assertThrows(EntityDoesNotExistException.class,
                     () -> { model.updateTrainingPlan(fakePlan); });
    }

    @Test
    void getAllTrainingPlansTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Bruno Silva",
                         32,
                         Gender.Other,
                         58,
                         167,
                         76,
                         7,
                         "Rua Henrique Silva 9, Lisboa",
                         "222417178",
                         "6lbpg5b846gm0q06dizdkjf@gmail.com",
                         12,
                         "Professional");
        UUID          userCode  = model.getUser("6lbpg5b846gm0q06dizdkjf@gmail.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2012, 10, 20);
        model.createTrainingPlan(userCode, startDate);
        model.createTrainingPlan(userCode, startDate.plusDays(7));
        List<TrainingPlan> plans = model.getAllTrainingPlans();
        assertEquals(2, plans.size());
    }

    @Test
    void addActivityToTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Elisabete Campos",
                         61,
                         Gender.Other,
                         78,
                         183,
                         92,
                         5,
                         "Rua Mariana Rocha 84, 10\u00ba dto., Porto",
                         "938463153",
                         "grg2ttw6gf7d@hotmail.com",
                         4,
                         "Occasional");
        UUID          userCode  = model.getUser("grg2ttw6gf7d@hotmail.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2014, 2, 5);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        Activity      activity  = new Trail(userCode,
                                      MakeItFitDate.of(2007, 3, 30),
                                      60,
                                      "Treino do 12h",
                                      "Zumba",
                                      25.039081131301383,
                                      2.988654500788889,
                                      9.16517814381002,
                                      Trail.TRAIL_TYPE_MEDIUM);
        assertDoesNotThrow(() -> { model.addActivityToTrainingPlan(planCode, activity, 3); });
        TrainingPlan plan = model.getTrainingPlan(planCode);
        assertTrue(plan.getActivities().stream().anyMatch(a -> a.getItem2().equals(activity)));
    }

    @Test
    void removeActivityFromTrainingPlanTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Hugo Coelho",
                         30,
                         Gender.Other,
                         88,
                         158,
                         95,
                         5,
                         "Rua Elisabete Gomes 28, Braga",
                         "929129262",
                         "956clpgsqb3zfqtkrtj3m4j95y@yahoo.com",
                         0,
                         "Amateur");
        UUID          userCode  = model.getUser("956clpgsqb3zfqtkrtj3m4j95y@yahoo.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2013, 6, 22);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        Activity      activity  = new PushUp(userCode,
                                       MakeItFitDate.of(2013, 8, 4),
                                       20,
                                       "Treino das 19h",
                                       "Corrida",
                                       26,
                                       8);
        model.addActivityToTrainingPlan(planCode, activity, 2);
        TrainingPlan plan         = model.getTrainingPlan(planCode);
        UUID         activityCode = plan.getActivities().get(0).getItem2().getCode();
        model.removeActivityFromTrainingPlan(planCode, activityCode);
        TrainingPlan updated = model.getTrainingPlan(planCode);
        assertTrue(updated.getActivities().stream().noneMatch(
            a -> a.getItem2().getCode().equals(activityCode)));
    }

    @Test
    void getTrainingPlansFromUserTest() {
        MakeItFit model = new MakeItFit();
        model.createUser("Andr\u00e9 Gon\u00e7alves",
                         74,
                         Gender.Male,
                         82,
                         157,
                         62,
                         9,
                         "Rua Humberto Gomes 84, Braga",
                         "275132538",
                         "oze83uwuethyt@yahoo.com",
                         12,
                         "Professional");
        UUID          userCode  = model.getUser("oze83uwuethyt@yahoo.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2004, 12, 21);
        model.createTrainingPlan(userCode, startDate);
        model.createTrainingPlan(userCode, MakeItFitDate.of(2024, 7, 2));
        List<TrainingPlan> userPlans = model.getTrainingPlansFromUser(userCode);
        assertEquals(2, userPlans.size());
        for (TrainingPlan plan : userPlans) {
            assertEquals(userCode, plan.getUserCode());
        }
    }

    @Test
    void testExecuteQueryHowManyKMsDone() {
        User user = new Professional("Eva Rocha",
                                     42,
                                     Gender.Male,
                                     70,
                                     176,
                                     68,
                                     8,
                                     "Rua Bruno Gomes 80, 1\u00ba esq., Porto",
                                     "297606718",
                                     "brwwd9retou@msn.com",
                                     20);
        user.addActivities(Arrays.asList(new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2011, 2, 7),
                                                         60,
                                                         "Passeio",
                                                         "Body Pump",
                                                         92,
                                                         2,
                                                         11.045573339455029),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2019, 9, 29),
                                                   15,
                                                   "Treino para emagrecer",
                                                   "Nata\u00e7\u00e3o",
                                                   15.390078436232976,
                                                   17.07394372206937,
                                                   7.581405040521219,
                                                   Trail.TRAIL_TYPE_HARD),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2009, 2, 24),
                                                   10,
                                                   "Treino do 12h",
                                                   "Bicicleta",
                                                   25.224591490468534,
                                                   7.392303607547496,
                                                   16.46409173391439,
                                                   Trail.TRAIL_TYPE_HARD),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2005, 7, 28),
                                                         30,
                                                         "Passeio",
                                                         "Caminhada",
                                                         46,
                                                         10,
                                                         11.967493787241835),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2020, 6, 20),
                                                     15,
                                                     "Treino das 19h",
                                                     "Bicicleta",
                                                     17.619837034694733,
                                                     10.256011324669236),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2015, 3, 31),
                                                   45,
                                                   "Passeio",
                                                   "Caminhada",
                                                   33.05388416591472,
                                                   16.56284355636689,
                                                   5.82656813242262,
                                                   Trail.TRAIL_TYPE_EASY),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2013, 5, 8),
                                                     15,
                                                     "Treino das 17h",
                                                     "Body Pump",
                                                     15.773893638140995,
                                                     11.589593365751636),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2010, 5, 24),
                                                     60,
                                                     "Passeio",
                                                     "Boxe",
                                                     14.490550632800469,
                                                     9.246296732326424),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2002, 12, 1),
                                                    45,
                                                    "Treino das 9h",
                                                    "Trail",
                                                    75,
                                                    7),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2002, 9, 11),
                                                         10,
                                                         "Treino das 19h",
                                                         "Bicicleta",
                                                         40,
                                                         4,
                                                         18.932682364344323),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2011, 3, 3),
                                                     45,
                                                     "Treino do 12h",
                                                     "Cycling",
                                                     14.86433224086229,
                                                     8.807921372038937),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2018, 6, 21),
                                                   45,
                                                   "Treino do 12h",
                                                   "Body Pump",
                                                   28.690639574931808,
                                                   5.279602800676162,
                                                   4.415492506191117,
                                                   Trail.TRAIL_TYPE_HARD),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2014, 7, 31),
                                                    60,
                                                    "Passeio",
                                                    "Trail",
                                                    34,
                                                    2),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2019, 9, 3),
                                                   60,
                                                   "Treino das 15h",
                                                   "Pilates",
                                                   21.79297405297466,
                                                   7.686812068919652,
                                                   15.948512979421714,
                                                   Trail.TRAIL_TYPE_HARD),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2021, 4, 8),
                                                         10,
                                                         "Treino para emagrecer",
                                                         "Trail",
                                                         20,
                                                         5,
                                                         16.407730676136012),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2020, 2, 23),
                                                         20,
                                                         "Treino das 17h",
                                                         "Boxe",
                                                         83,
                                                         4,
                                                         18.465559236878832),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2004, 9, 30),
                                                     10,
                                                     "Treino das 9h",
                                                     "Bicicleta",
                                                     1.271733384958209,
                                                     13.243685419915973),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2004, 2, 13),
                                                    45,
                                                    "Treino das 19h",
                                                    "Cycling",
                                                    95,
                                                    10),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2013, 5, 26),
                                                    10,
                                                    "Treino das 19h",
                                                    "Caminhada",
                                                    76,
                                                    7),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2022, 4, 11),
                                                    10,
                                                    "Treino do 12h",
                                                    "Nata\u00e7\u00e3o",
                                                    10,
                                                    1),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2004, 4, 30),
                                                     20,
                                                     "Passeio",
                                                     "Zumba",
                                                     1.5054837691614629,
                                                     6.506005272394287)));
        MakeItFit makeItFit = new MakeItFit();
        makeItFit.addUser(user);
        assertEquals(0.1896779984211408, makeItFit.executeQueryHowManyKMsDone(user.getEmail()));
    }

    @Test
    void testExecuteQueryHowManyKMsDoneDates() {
        User user = new Occasional("Andr\u00e9 Lopes",
                                   69,
                                   Gender.Female,
                                   88,
                                   179,
                                   93,
                                   5,
                                   "Rua Dalila Rocha 75, Braga",
                                   "920612476",
                                   "lvgu0jf@aol.com",
                                   5);
        user.addActivities(Arrays.asList(new Running(user.getCode(),
                                                     MakeItFitDate.of(2016, 11, 24),
                                                     15,
                                                     "Treino das 9h",
                                                     "Body Pump",
                                                     15.570889269773001,
                                                     9.859698070638851),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2022, 5, 11),
                                                     15,
                                                     "Treino das 19h",
                                                     "Zumba",
                                                     9.063705061307145,
                                                     8.100244793869752),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2002, 9, 4),
                                                    15,
                                                    "Treino do 12h",
                                                    "Nata\u00e7\u00e3o",
                                                    24,
                                                    8),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2006, 10, 24),
                                                   60,
                                                   "Passeio",
                                                   "Pilates",
                                                   1.4572072651176184,
                                                   17.410668851004054,
                                                   4.397148346229097,
                                                   Trail.TRAIL_TYPE_MEDIUM),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2022, 6, 8),
                                                    30,
                                                    "Treino das 19h",
                                                    "Body Pump",
                                                    43,
                                                    10),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2014, 5, 10),
                                                   45,
                                                   "Passeio",
                                                   "Corrida",
                                                   8.868111920649987,
                                                   15.167450714849423,
                                                   7.532321189374677,
                                                   Trail.TRAIL_TYPE_EASY),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2020, 2, 7),
                                                         20,
                                                         "Treino das 17h",
                                                         "Caminhada",
                                                         74,
                                                         9,
                                                         10.469172664476464)));
        MakeItFit makeItFit = new MakeItFit();
        makeItFit.addUser(user);
        assertEquals(0.03350270625173013,
                     makeItFit.executeQueryHowManyKMsDone(user.getEmail(),
                                                          MakeItFitDate.of(2007, 10, 30),
                                                          MakeItFitDate.of(2107, 10, 30)));
    }

    @Test
    void testExecuteQueryHowManyAltimetryDone() {
        User user = new Amateur("Gabriel Dias",
                                48,
                                Gender.Female,
                                51,
                                154,
                                68,
                                6,
                                "Rua Carlos Lopes 2, 2\u00ba esq., Lisboa",
                                "916267513",
                                "nbb5atscbdyk15opmp7tqylkwm6v1r@aol.com");
        user.addActivities(Arrays.asList(
            new Running(user.getCode(),
                        MakeItFitDate.of(2019, 2, 16),
                        20,
                        "Treino das 9h",
                        "Trail",
                        19.187912637182325,
                        9.396748033351052),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2022, 1, 20),
                      15,
                      "Treino das 17h",
                      "Trail",
                      38.7398344479116,
                      13.210470494992759,
                      11.394675742787246,
                      Trail.TRAIL_TYPE_MEDIUM),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2015, 9, 12),
                      45,
                      "Treino das 19h",
                      "Body Pump",
                      3.432565625440556,
                      9.958385861372607,
                      7.49435019907562,
                      Trail.TRAIL_TYPE_EASY),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2016, 1, 28),
                      60,
                      "Treino das 19h",
                      "Zumba",
                      40.688659544953666,
                      0.9504434541563924,
                      8.173074354396185,
                      Trail.TRAIL_TYPE_MEDIUM),
            new PushUp(user.getCode(),
                       MakeItFitDate.of(2017, 11, 4),
                       60,
                       "Treino das 17h",
                       "Muscula\u00e7\u00e3o",
                       5,
                       8),
            new Running(user.getCode(),
                        MakeItFitDate.of(2015, 11, 15),
                        30,
                        "Treino do 12h",
                        "Body Pump",
                        1.771430982954088,
                        14.600891243698694),
            new WeightSquat(user.getCode(),
                            MakeItFitDate.of(2022, 2, 8),
                            10,
                            "Treino do 12h",
                            "Body Pump",
                            65,
                            10,
                            13.517084641631925),
            new PushUp(user.getCode(),
                       MakeItFitDate.of(2022, 1, 24),
                       15,
                       "Treino das 17h",
                       "Muscula\u00e7\u00e3o",
                       78,
                       3),
            new PushUp(user.getCode(), MakeItFitDate.of(2007, 6, 12), 60, "Passeio", "Boxe", 69, 2),
            new PushUp(user.getCode(),
                       MakeItFitDate.of(2002, 3, 25),
                       45,
                       "Treino para emagrecer",
                       "Muscula\u00e7\u00e3o",
                       20,
                       8),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2014, 9, 16),
                      30,
                      "Passeio",
                      "Bicicleta",
                      12.83339471151667,
                      1.5429313047786701,
                      14.473924512967496,
                      Trail.TRAIL_TYPE_MEDIUM),
            new WeightSquat(user.getCode(),
                            MakeItFitDate.of(2013, 2, 8),
                            15,
                            "Treino das 9h",
                            "Corrida",
                            23,
                            4,
                            15.933090812672262),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2017, 9, 28),
                      45,
                      "Treino do 12h",
                      "Corrida",
                      36.086073739377156,
                      6.056838292300661,
                      2.893737305738533,
                      Trail.TRAIL_TYPE_MEDIUM),
            new Running(user.getCode(),
                        MakeItFitDate.of(2018, 12, 31),
                        20,
                        "Treino das 9h",
                        "Caminhada",
                        5.081110466447692,
                        11.72683461286054),
            new PushUp(user.getCode(),
                       MakeItFitDate.of(2015, 11, 14),
                       20,
                       "Treino das 9h",
                       "Boxe",
                       15,
                       8),
            new Trail(user.getCode(),
                      MakeItFitDate.of(2024, 1, 25),
                      10,
                      "Passeio",
                      "Muscula\u00e7\u00e3o",
                      2.947638473807733,
                      10.69604075534339,
                      11.656096093289959,
                      Trail.TRAIL_TYPE_EASY)));
        MakeItFit makeItFit = new MakeItFit();
        makeItFit.addUser(user);
        assertEquals(98.50096837119952,
                     makeItFit.executeQueryHowManyAltimetryDone(user.getEmail()));
    }

    @Test
    void testExecuteQueryHowManyAltimetryDoneDates() {
        User user = new Occasional("Humberto Rocha",
                                   57,
                                   Gender.Other,
                                   99,
                                   155,
                                   82,
                                   2,
                                   "Rua Hugo Lopes 69, Braga",
                                   "203463409",
                                   "kgauixi2fpzcyyn4d@hotmail.com",
                                   14);
        user.addActivities(Arrays.asList(new Running(user.getCode(),
                                                     MakeItFitDate.of(2024, 9, 18),
                                                     45,
                                                     "Treino do 12h",
                                                     "Boxe",
                                                     2.127558026863543,
                                                     9.646758950248863),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2011, 5, 2),
                                                         60,
                                                         "Treino das 9h",
                                                         "Caminhada",
                                                         58,
                                                         3,
                                                         17.327211768930407),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2011, 6, 28),
                                                         60,
                                                         "Treino das 17h",
                                                         "Bicicleta",
                                                         35,
                                                         9,
                                                         10.201415708350956),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2004, 9, 21),
                                                     60,
                                                     "Treino das 9h",
                                                     "Yoga",
                                                     5.885934178797484,
                                                     11.475310264915988),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2016, 4, 22),
                                                    60,
                                                    "Treino das 15h",
                                                    "Zumba",
                                                    56,
                                                    8),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2007, 7, 2),
                                                    10,
                                                    "Treino das 17h",
                                                    "Body Pump",
                                                    57,
                                                    6),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2014, 5, 4),
                                                   10,
                                                   "Treino das 19h",
                                                   "Bicicleta",
                                                   39.47604653370207,
                                                   3.436095853541736,
                                                   4.430778910532952,
                                                   Trail.TRAIL_TYPE_HARD),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2015, 3, 28),
                                                    15,
                                                    "Treino do 12h",
                                                    "Trail",
                                                    66,
                                                    5),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2013, 4, 9),
                                                         30,
                                                         "Treino do 12h",
                                                         "Yoga",
                                                         23,
                                                         1,
                                                         11.940300987632572),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2001, 6, 13),
                                                     10,
                                                     "Treino para emagrecer",
                                                     "Pilates",
                                                     3.978408588082378,
                                                     6.58014163369703),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2021, 11, 27),
                                                     20,
                                                     "Treino das 15h",
                                                     "Zumba",
                                                     5.724956894304916,
                                                     11.055486129083533),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2022, 9, 4),
                                                    60,
                                                    "Treino das 17h",
                                                    "Zumba",
                                                    69,
                                                    7),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2006, 1, 14),
                                                     15,
                                                     "Treino das 15h",
                                                     "Caminhada",
                                                     1.7125679003623988,
                                                     6.096254976043829),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2018, 7, 19),
                                                   60,
                                                   "Treino das 19h",
                                                   "Cycling",
                                                   31.344311309291154,
                                                   8.647423814519621,
                                                   2.642914636091514,
                                                   Trail.TRAIL_TYPE_EASY),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2004, 11, 25),
                                                    20,
                                                    "Treino das 15h",
                                                    "Cycling",
                                                    41,
                                                    5),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2010, 12, 7),
                                                   60,
                                                   "Treino das 9h",
                                                   "Corrida",
                                                   23.158618315760624,
                                                   14.825138384864637,
                                                   18.974687407706266,
                                                   Trail.TRAIL_TYPE_EASY),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2011, 10, 25),
                                                    60,
                                                    "Treino para emagrecer",
                                                    "Boxe",
                                                    12,
                                                    4),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2009, 10, 5),
                                                     10,
                                                     "Treino das 15h",
                                                     "Corrida",
                                                     14.638090993295991,
                                                     12.721056574758368),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2003, 10, 8),
                                                   20,
                                                   "Treino das 19h",
                                                   "Zumba",
                                                   8.330963744507685,
                                                   9.080209088314149,
                                                   0.6548804334015729,
                                                   Trail.TRAIL_TYPE_MEDIUM),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2004, 4, 21),
                                                         10,
                                                         "Treino das 17h",
                                                         "Yoga",
                                                         68,
                                                         6,
                                                         12.152049543798514),
                                         new Running(user.getCode(),
                                                     MakeItFitDate.of(2020, 5, 5),
                                                     20,
                                                     "Treino do 12h",
                                                     "Caminhada",
                                                     5.18281094538362,
                                                     9.121962202018342),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2018, 7, 5),
                                                    15,
                                                    "Treino das 9h",
                                                    "Trail",
                                                    89,
                                                    8),
                                         new PushUp(user.getCode(),
                                                    MakeItFitDate.of(2005, 3, 15),
                                                    60,
                                                    "Treino das 15h",
                                                    "Zumba",
                                                    79,
                                                    5),
                                         new WeightSquat(user.getCode(),
                                                         MakeItFitDate.of(2007, 10, 9),
                                                         20,
                                                         "Passeio",
                                                         "Zumba",
                                                         64,
                                                         1,
                                                         10.538889571649989),
                                         new Trail(user.getCode(),
                                                   MakeItFitDate.of(2007, 1, 5),
                                                   15,
                                                   "Treino das 15h",
                                                   "Muscula\u00e7\u00e3o",
                                                   4.888998871498381,
                                                   4.761069336517841,
                                                   5.996197389955111,
                                                   Trail.TRAIL_TYPE_MEDIUM)));
        MakeItFit makeItFit = new MakeItFit();
        makeItFit.addUser(user);
        assertEquals(0.0,
                     makeItFit.executeQueryHowManyAltimetryDone(user.getEmail(),
                                                                MakeItFitDate.of(2020, 7, 11),
                                                                MakeItFitDate.of(2120, 7, 11)));
    }
}
