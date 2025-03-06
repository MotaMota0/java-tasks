package org.example.srsp;



import java.sql.*;

import java.util.Scanner;



public class FindTOTable {

    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw = "user";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1: find with name\n" +
                "2: find with IKT\n" +
                "3: find by university\n" +
                "4: find by score between ");
        int n = sc.nextInt();
        sc.nextLine();

        switch (n) {
            case 1:
                System.out.println("Enter the Fullname: ");
                String name = sc.nextLine();
                findByFIO(name.toUpperCase());
                break;

            case 2:
                System.out.println("Enter IKT (IIN): ");
                String iin = sc.next();
                findByIKT(iin);
                break;

            case 3:
                System.out.println("Enter University Code: ");
                int uniCode = sc.nextInt();
                findByUniversity(uniCode);
                break;

            case 4:
                System.out.println("Enter Minimum Score: ");
                int minScore = sc.nextInt();
                System.out.println("Enter Maximum Score: ");
                int maxScore = sc.nextInt();
                findByScore(minScore, maxScore);
                break;

            default:
                System.out.println("Invalid option!");
        }
        sc.close();
    }

    public static void findByFIO(String name) {
        String query = "SELECT * FROM public.pdf_table WHERE TRIM(name) = ?";

        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(query)) {

            pr.setString(1, name);
            printResults(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findByIKT(String iin) {
        String query = "SELECT * FROM public.pdf_table WHERE iin = ?";

        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(query)) {

            pr.setString(1, iin);
            printResults(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findByUniversity(int uniCode) {
        String query = "SELECT * FROM public.pdf_table WHERE uniCode = ?";

        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(query)) {

            pr.setInt(1, uniCode);
            printResults(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findByScore(int minScore, int maxScore) {
        String query = "SELECT * FROM public.pdf_table WHERE score BETWEEN ? AND ?";

        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(query)) {

            pr.setInt(1, minScore);
            pr.setInt(2, maxScore);
            printResults(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printResults(PreparedStatement pr) throws SQLException {
        ResultSet resultSet = pr.executeQuery();
        boolean found = false;
        while (resultSet.next()) {
            found = true;
            System.out.println("IIN: " + resultSet.getString("iin"));
            System.out.println("Name: " + resultSet.getString("name"));
            System.out.println("Score: " + resultSet.getInt("score"));
            System.out.println("University Code: " + resultSet.getInt("uniCode"));
            System.out.println("---------------------------");
        }
        if (!found) {
            System.out.println("No records found.");
        }
    }

}
