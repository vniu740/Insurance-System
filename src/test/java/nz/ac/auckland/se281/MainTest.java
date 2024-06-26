package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class, // Uncomment this line when to start Task 2
  MainTest.Task3.class, // Uncomment this line when to start Task 3
  MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {
  public static class Task1 extends CliTest {
    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_empty_database() throws Exception {
      runCommands(PRINT_DB);
      assertContains("Database has 0 profiles.");
    }

    @Test
    public void T1_02_add_one_client() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_03_add_one_client_with_info() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertContains("1: Jordan, 21");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_04_ignore_short_name() throws Exception {
      runCommands(CREATE_PROFILE, "Jo", "21", PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'Jo' is an invalid username, it should be at least 3 characters long. No profile was"
              + " created.");
      assertDoesNotContain("Database has 1 profiles", true);
      assertDoesNotContain("New profile created", true);
      assertDoesNotContain("21");
    }

    @Test
    public void T1_05_add_two_clients() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", CREATE_PROFILE, "Tom", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
    }

    @Test
    public void T1_06_username_to_titlecase() throws Exception {
      runCommands(CREATE_PROFILE, "jorDan", "21", CREATE_PROFILE, "TOM", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("jorDan");
      assertDoesNotContain("TOM");
    }
  }

  public static class Task2 extends CliTest {
    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_01_load_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom"));

      assertContains("Profile loaded for Tom.");
      assertDoesNotContain("No profile found for Tom. Profile not loaded.", true);
    }

    @Test
    public void T2_02_load_profile_not_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Alex"));

      assertContains("No profile found for Alex. Profile not loaded.");
      assertDoesNotContain("Profile loaded for Alex.", true);
    }

    @Test
    public void T2_03_load_profile_found_display() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom", PRINT_DB));

      assertContains("Profile loaded for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("*** 2: Tom, 25");
      assertContains("3: Jenny, 23");
    }

    @Test
    public void T2_04_load_profile_switch_profiles() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "tom", LOAD_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("*** 3: Jenny, 23");
      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
    }

    @Test
    public void T2_05_unload_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", UNLOAD_PROFILE, PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("Profile unloaded for Jenny.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_06_unload_invalid_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jen", UNLOAD_PROFILE, PRINT_DB));

      assertContains("No profile is currently loaded.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_07_delete_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "jordan", PRINT_DB));

      assertContains("Profile deleted for Jordan.");
      assertContains("Database has 2 profiles:");
      assertContains("1: Tom, 25");
      assertContains("2: Jenny, 23");
      assertDoesNotContain("Jordan, 21", true);
    }

    @Test
    public void T2_08_delete_profile_while_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", DELETE_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Cannot delete profile for Jenny while loaded. No profile was deleted.");
      assertDoesNotContain("Profile deleted for Jenny", true);

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");
    }
  }

  public static class Task3 extends CliTest {
    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_01_cannot_add_policy_without_loaded_profile() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, POLICY_HOME, options("1000000", "20 Symonds Street", "yes")));

      assertContains("Need to load a profile in order to create a policy.");
      assertDoesNotContain("New home policy created", true);
    }

    @Test
    public void T3_02_add_home_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 1 policy");

      assertContains("New home policy created for Jenny.");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void T3_03_add_car_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Tom",
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("New car policy created for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("*** 2: Tom, 25, 1 policy");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5580 -> $5580)");
    }

    @Test
    public void T3_04_two_different_policies_home_life_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 2 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $11070)");
    }

    @Test
    public void T3_05_three_policies_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_HOME,
              options("1000000", "20 Queen Street", "no"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 3 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains(
          "Home Policy (20 Queen Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
    }

    @Test
    public void T3_06_life_policy_over_age_limit() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          101,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("100000"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("Profile loaded for Jenny.");
      assertContains("Jenny is over the age limit. No policy was created.");

      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 101, 0 policies");

      assertDoesNotContain("New life policy created for Jenny.", true);
      assertDoesNotContain("Life Policy (Sum Insured", true);
    }

    @Test
    public void T3_07_two_policies_one_profile_ignore_zero_policy_total_costs() throws Exception {
      runCommands(
          unpack( //
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "Tom", //
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"), //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              LOAD_PROFILE,
              "Jenny", //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              PRINT_DB));

      assertContains("2: Tom, 25, 2 policies for a total of $22950");
      assertContains("3: Jenny, 23, 1 policy for a total of $8250");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5500 -> $4950)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $8250 -> $8250)");
    }
  }

  public static class YourTests extends CliTest {
    public YourTests() {
      super(Main.class);
    }

    /*  @Test
    public void TY_01_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(PRINT_DB);
      assertContains("");
    }*/

    /*@Test
    public void TY_02_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(PRINT_DB);
      assertContains("");
    }*/

    @Test
    public void T1_01_add_three_clients_with_info() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "JORdan",
          "21",
          CREATE_PROFILE,
          "TOm",
          "25",
          CREATE_PROFILE,
          "LINDa",
          "19",
          PRINT_DB);
      assertContains("New profile created for Jordan with age 21.");
      assertContains("New profile created for Tom with age 25.");
      assertContains("New profile created for Linda with age 19.");
      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Linda, 19");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("Database has 2 profiles", true);
    }

    @Test
    public void T1_02_add_non_unique_username() throws Exception {
      runCommands(CREATE_PROFILE, "JORdan", "21", CREATE_PROFILE, "jORDAN", "19", PRINT_DB);
      assertContains("New profile created for Jordan with age 21.");
      assertContains("Usernames must be unique. No profile was created for 'Jordan'.");
      assertContains("Database has 1 profile:");
      assertContains("1: Jordan, 21");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 2 profiles", true);
    }

    @Test
    public void T1_03_age_is_a_word() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "twenty", PRINT_DB);
      assertContains(
          "'twenty' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jordan.");
      assertContains("Database has 0 profiles");
      assertDoesNotContain("Database has 1 profiles", true);
      assertDoesNotContain("Database has 2 profiles", true);
      assertDoesNotContain("New profile created for Jordan with age 21.", true);
    }

    @Test
    public void T1_04_age_is_a_negative_integer() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "-19", PRINT_DB);
      assertContains(
          "'-19' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jordan.");
      assertContains("Database has 0 profiles");
      assertDoesNotContain("Database has 1 profiles", true);
      assertDoesNotContain("Database has 2 profiles", true);
      assertDoesNotContain("New profile created for Jordan with age 21.", true);
    }

    @Test
    public void T1_05_add_two_clients_and_non_unique_username() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "JORdan",
          "21",
          CREATE_PROFILE,
          "TOm",
          "25",
          CREATE_PROFILE,
          "jordan",
          "19",
          PRINT_DB);
      assertContains("New profile created for Jordan with age 21.");
      assertContains("New profile created for Tom with age 25.");
      assertContains("Usernames must be unique. No profile was created for 'Jordan'.");
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("Database has 3 profiles", true);
    }

    @Test
    public void T1_06_create_profile_all_caps() throws Exception {
      runCommands(CREATE_PROFILE, "JORDAN", "19", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 19.");
      assertContains("1: Jordan, 19");
      assertDoesNotContain("New profile created for JORDAN with age 19.");
    }

    @Test
    public void T1_07_create_profile_all_lower_caps() throws Exception {
      runCommands(CREATE_PROFILE, "jordan", "19", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 19.");
      assertContains("1: Jordan, 19");
      assertDoesNotContain("New profile created for jordan with age 19.");
    }

    @Test
    public void T1_08_add_four_clients_with_info() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "JORdan",
          "21",
          CREATE_PROFILE,
          "TOm",
          "25",
          CREATE_PROFILE,
          "LINDa",
          "19",
          CREATE_PROFILE,
          "peter",
          "81",
          PRINT_DB);
      assertContains("New profile created for Jordan with age 21.");
      assertContains("New profile created for Tom with age 25.");
      assertContains("New profile created for Linda with age 19.");
      assertContains("New profile created for Peter with age 81.");
      assertContains("Database has 4 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Linda, 19");
      assertContains("4: Peter, 81");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("Database has 2 profiles", true);
      assertDoesNotContain("Database has 3 profiles", true);
    }

    @Test
    public void T1_09_add_five_clients_with_info() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "JORdan",
          "21",
          CREATE_PROFILE,
          "TOm",
          "25",
          CREATE_PROFILE,
          "LINDa",
          "19",
          CREATE_PROFILE,
          "peter",
          "81",
          CREATE_PROFILE,
          "REBECCA",
          "100",
          PRINT_DB);
      assertContains("New profile created for Jordan with age 21.");
      assertContains("New profile created for Tom with age 25.");
      assertContains("New profile created for Linda with age 19.");
      assertContains("New profile created for Peter with age 81.");
      assertContains("New profile created for Rebecca with age 100.");
      assertContains("Database has 5 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Linda, 19");
      assertContains("4: Peter, 81");
      assertContains("5: Rebecca, 100");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("Database has 2 profiles", true);
      assertDoesNotContain("Database has 3 profiles", true);
      assertDoesNotContain("Database has 4 profiles", true);
    }
    // Full tests Cases from CheckPoint #1
    @Test
    public void T1_07_web_ignore_short_name_to_titlecase() throws Exception {
      runCommands(CREATE_PROFILE, "aL", "21", PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'Al' is an invalid username, it should be at least 3 characters long. No profile was"
              + " created.");
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("New profile created", true);
      assertDoesNotContain("21");
    }

    @Test
    public void T1_08_web_add_five_clients() throws Exception {
      runCommands( //
          CREATE_PROFILE,
          "Jordan",
          "21", //
          CREATE_PROFILE,
          "Jenny",
          "22", //
          CREATE_PROFILE,
          "TOM",
          "23", //
          CREATE_PROFILE,
          "tOmmY",
          "24", //
          CREATE_PROFILE,
          "aLeX",
          "25", //
          PRINT_DB);

      assertContains("Database has 5 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Jenny, 22");
      assertContains("3: Tom, 23");
      assertContains("4: Tommy, 24");
      assertContains("5: Alex, 25");
    }

    @Test
    public void T1_09_web_add_ignore_duplicate() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", CREATE_PROFILE, "Jordan", "35", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("1: Jordan, 21");

      assertContains("Usernames must be unique. No profile was created for 'Jordan'.");

      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 2 profiles", true);
      assertDoesNotContain("Jordan, 35", true);
    }

    @Test
    public void T1_10_web_add_ignore_duplicate_added_later() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "tom",
          "21", //
          CREATE_PROFILE,
          "jordan",
          "25", //
          CREATE_PROFILE,
          "Jenny",
          "23", //
          CREATE_PROFILE,
          "TOM",
          "32", //
          PRINT_DB);
      assertContains("Database has 3 profiles:");
      assertContains("1: Tom, 21");
      assertContains("2: Jordan, 25");
      assertContains("3: Jenny, 23");

      assertContains("Usernames must be unique. No profile was created for 'Tom'.");

      assertDoesNotContain("Database has 4 profiles", true);
      assertDoesNotContain("Tom, 32", true);
    }

    @Test
    public void T1_11_web_ignore_invalid_age_negative() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "-1", PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'-1' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jordan.");
      assertDoesNotContain("Database has 1 profile", true);
      assertDoesNotContain("Jordan, -1", true);
      assertDoesNotContain("New profile created", true);
    }

    @Test
    public void T1_12_web_add_success_after_invalid_age() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "-1", CREATE_PROFILE, "Jordan", "20", PRINT_DB);
      assertContains(
          "'-1' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jordan.");
      assertContains("Database has 1 profile:");
      assertContains("1: Jordan, 20");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Jordan, -1", true);
    }

    @Test
    public void T2_01_load_profile_in_database_then_load_profile_not_in_database()
        throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jordan", LOAD_PROFILE, "jen", PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("No profile found for Jen. Profile not loaded.");

      assertContains("*** 1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_02_create_profile_while_another_is_loaded() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS, LOAD_PROFILE, "jordan", CREATE_PROFILE, "lucy", "45", PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("Cannot create a new profile. First unload the profile for Jordan.");

      assertContains("*** 1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
      assertDoesNotContain("3: Lucy, 45", true);
    }

    @Test
    public void T2_03_unload_profile_that_is_loaded() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jordan", UNLOAD_PROFILE, PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("Profile unloaded for Jordan.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 2: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_04_unload_profile_when_no_profile_is_loaded() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, UNLOAD_PROFILE, PRINT_DB));

      assertContains("No profile is currently loaded.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 2: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
      assertDoesNotContain("Profile unloaded for Jordan.", true);
      assertDoesNotContain("Profile unloaded for Tom.", true);
      assertDoesNotContain("Profile unloaded for Jenny.", true);
    }

    @Test
    public void T2_05_delete_profile_number_two_in__three_person_database() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "jordan", PRINT_DB));

      assertContains("Database has 2 profiles:");
      assertContains("Profile deleted for Jordan.");

      assertContains("1: Tom, 25");
      assertContains("2: Jenny, 23");

      assertDoesNotContain("1: Jordan, 21", true);
      assertDoesNotContain("2: Tom, 25", true);
      assertDoesNotContain("3: Jenny, 23", true);
    }

    @Test
    public void T2_06_delete_profile_number_two_in__four_person_database() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS, CREATE_PROFILE, "Lucy", "55", DELETE_PROFILE, "Tom", PRINT_DB));

      assertContains("Profile deleted for Tom.");
      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Jenny, 23");
      assertContains("3: Lucy, 55");

      assertDoesNotContain("2: Tom, 25", true);
      assertDoesNotContain("3: Jenny, 23", true);
      assertDoesNotContain("4: Lucy, 55", true);
    }

    @Test
    public void T2_07_delete_profile_three_times() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              CREATE_PROFILE,
              "Lucy",
              "55",
              DELETE_PROFILE,
              "Tom",
              DELETE_PROFILE,
              "Jordan",
              DELETE_PROFILE,
              "Jenny",
              PRINT_DB));

      assertContains("Profile deleted for Tom.");
      assertContains("Profile deleted for Jordan.");
      assertContains("Profile deleted for Jenny.");
      assertContains("Database has 1 profile:");
      assertContains("1: Lucy, 55");

      assertDoesNotContain("1: Jordan, 21", true);
      assertDoesNotContain("2: Tom, 25", true);
      assertDoesNotContain("3: Jenny, 23", true);
      assertDoesNotContain("4: Lucy, 55", true);
    }

    @Test
    public void T2_08_delete_profile_with_no_profile_in_database() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, CREATE_PROFILE, DELETE_PROFILE, "alex", PRINT_DB));

      assertContains("No profile found for Alex. No profile was deleted.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");
    }

    @Test
    public void T3_01_add_car_policy_no_mechanical_under_25() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "jordan",
              POLICY_CAR,
              options("55000", "Mazda Demio", "SUB123", "no"),
              PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("New car policy created for Jordan.");

      assertContains("Database has 3 profiles:");
      assertContains("*** 1: Jordan, 21, 1 policy");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Mazda Demio, Sum Insured: $55000, Premium: $8250 -> $8250)");
    }

    @Test
    public void T3_02_add_car_policy_yes_mechanical_under_25() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "jordan",
              POLICY_CAR,
              options("55000", "Mazda Demio", "SUB123", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("New car policy created for Jordan.");

      assertContains("Database has 3 profiles:");
      assertContains("*** 1: Jordan, 21, 1 policy");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Mazda Demio, Sum Insured: $55000, Premium: $8330 -> $8330)");
    }

    @Test
    public void T3_03_add_car_policy_no_mechanical_over_25() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "tom",
              POLICY_CAR,
              options("55000", "Mazda Demio", "SUB123", "no"),
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("New car policy created for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("*** 2: Tom, 25, 1 policy");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Mazda Demio, Sum Insured: $55000, Premium: $5500 -> $5500)");
    }

    @Test
    public void T3_04_add_life_policy_when_client_already_has_one() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "tom",
              POLICY_LIFE,
              options("1000000"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("New life policy created for Tom.");
      assertContains("Tom already has a life policy. No new policy was created.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("*** 2: Tom, 25, 1 policy");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12500 -> $12500)");

      assertDoesNotContain("*** 2: Tom, 25, 2 policies");
    }

    @Test
    public void T3_05_add_one_home_policy_loaded_profile_check_total_to_pay() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies for a total of $0");
      assertContains("2: Tom, 25, 0 policies for a total of $0");
      assertContains("*** 3: Jenny, 23, 1 policy for a total of $20000");

      assertContains("New home policy created for Jenny.");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void T3_06_two_different_policies_home_life_one_profile_check_total_to_pay()
        throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies for a total of $0");
      assertContains("2: Tom, 25, 0 policies for a total of $0");
      assertContains("*** 3: Jenny, 23, 2 policies for a total of $29070");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $11070)");
    }

    @Test
    public void T3_07_three_policies_one_profile_check_total_to_pay() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_HOME,
              options("1000000", "20 Queen Street", "no"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies for a total of $0");
      assertContains("2: Tom, 25, 0 policies for a total of $0");
      assertContains("*** 3: Jenny, 23, 3 policies for a total of $33840");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains(
          "Home Policy (20 Queen Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
    }

    @Test
    public void T3_08_three_policies_three_profiles_check_total_to_pay() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_CAR,
              options("60000", "Toyota Aqua", "HNK100", "no"),
              POLICY_HOME,
              options("1000000", "20 Queen Street", "no"),
              POLICY_LIFE,
              options("1000000"),
              UNLOAD_PROFILE,
              LOAD_PROFILE,
              "jordan",
              POLICY_LIFE,
              options("2000000"),
              UNLOAD_PROFILE,
              LOAD_PROFILE,
              "tom",
              POLICY_CAR,
              options("88000", "Mazda Demio", "SUB123", "yes"),
              POLICY_HOME,
              options("5000000", "123 Apple Street", "yes"),
              UNLOAD_PROFILE,
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New car policy created for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");
      assertContains("Profile unloaded for Jenny.");

      assertContains("Profile loaded for Jordan.");
      assertContains("New life policy created for Jordan.");
      assertContains("Profile unloaded for Jordan.");

      assertContains("Profile loaded for Tom.");
      assertContains("New car policy created for Tom.");
      assertContains("New home policy created for Tom.");
      assertContains("Profile unloaded for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 1 policy for a total of $24200");
      assertContains("2: Tom, 25, 2 policies for a total of $97992");
      assertContains("3: Jenny, 23, 3 policies for a total of $25040");

      assertContains("Life Policy (Sum Insured: $2000000, Premium: $24200 -> $24200)");

      assertContains("Car Policy (Mazda Demio, Sum Insured: $88000, Premium: $8880 -> $7992)");
      assertContains(
          "Home Policy (123 Apple Street, Sum Insured: $5000000, Premium: $100000 -> $90000)");

      assertContains("Car Policy (Toyota Aqua, Sum Insured: $60000, Premium: $9000 -> $7200)");
      assertContains(
          "Home Policy (20 Queen Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
    }
  }

  private static final Object[] CREATE_SOME_CLIENTS =
      new Object[] {
        CREATE_PROFILE, "Jordan", "21", //
        CREATE_PROFILE, "Tom", "25", //
        CREATE_PROFILE, "Jenny", "23",
      };

  private static Object[] unpack(Object[] commands, Object... more) {
    final List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));
    all.addAll(List.of(more));
    return all.toArray(new Object[all.size()]);
  }

  private static String[] options(String... options) {
    final List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
