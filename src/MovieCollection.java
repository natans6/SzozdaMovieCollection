import java.util.ArrayList;
import java.io.File;
import  java.io.IOException;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieCollection(){
        start();
    }

    public void start(){
        load(movies);
        mainMenu();
    }
    public void load(ArrayList<Movie> movies){
        try{
            File myFile = new File("src//movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while(fileScanner.hasNext()){
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                String cast = splitData[1];
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);
                Movie movie = new Movie(title, cast, director, overview, runtime, userRating);
                movies.add(movie);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void mainMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";
        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    public void searchTitles(){
        Scanner scan = new Scanner(System.in);
        ArrayList<Movie> titles = new ArrayList<Movie>();
        int increment = 1;
        System.out.print("Enter a title search term: ");
        String searchTerm = scan.nextLine();
        for (int i = 0; i < movies.size(); i++){
            if (movies.get(i).getTitle().toLowerCase().contains(searchTerm)){
                titles.add(movies.get(i));
            }
        }
        if (titles.size() == 0){
            System.out.println();
            System.out.println("No movie titles match that search term!");
        } else {
            for (int i = 1; i < titles.size(); i++){
                Movie currentWord = titles.get(i);
                int index = i;
                while (index > 0 && currentWord.getTitle().compareTo(titles.get(index - 1).getTitle()) < 0){
                    titles.set(index, titles.get(index - 1));
                    index--;
                }
                titles.set(index, currentWord);
            }
            for (int i = 0; i < titles.size(); i++){
                System.out.println(increment + ". " + titles.get(i).getTitle());
                increment++;
            }

            System.out.println("Which movie would you like to learn more about? ");
            System.out.print("Enter number: ");
            int userNum = scan.nextInt();
            System.out.println();
            System.out.println("Title: " + titles.get(userNum - 1).getTitle());
            System.out.println("Runtime: " + titles.get(userNum - 1).getRuntime() + " minutes");
            System.out.println("Directed by: " + titles.get(userNum - 1).getDirector());
            System.out.println("Cast: " + titles.get(userNum - 1).getCast());
            System.out.println("Overview: " + titles.get(userNum - 1).getOverview());
            System.out.println("User rating: " + titles.get(userNum - 1).getUserRating());
        }
    }
    public void searchCast(){
        int increment = 1;
        ArrayList<Movie> casts = new ArrayList<Movie>();
        ArrayList<String> castMembers = new ArrayList<String>();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a person to search for (first or last name): ");
        String searchTerm = scan.nextLine();
        for (int i = 0; i < movies.size(); i++){
            if (movies.get(i).getCast().toLowerCase().contains(searchTerm)){
                casts.add(movies.get(i));
            }
        }
        for (int i = 0; i < casts.size(); i++){
            String[] splitCast = casts.get(i).getCast().split("\\|");
            for (int j = 0; j < splitCast.length; j++){
                if (splitCast[j].toLowerCase().contains(searchTerm)){
                    castMembers.add(splitCast[j]);
                }
            }
        }
        if (castMembers.size() == 0){
            System.out.println();
            System.out.println("No results match your search!");
        } else {
            for (int i = 1; i < castMembers.size(); i++){
                String currentWord = castMembers.get(i);
                int index = i;
                while (index > 0 && currentWord.compareTo(castMembers.get(index - 1)) < 0){
                    castMembers.set(index, castMembers.get(index - 1));
                    index--;
                }
                castMembers.set(index, currentWord);
            }
            for (int i = 0; i < castMembers.size(); i++){
                String currentMember = castMembers.get(i);
                for (int k = i + 1; k < castMembers.size(); k++){
                    if (currentMember.equals(castMembers.get(k))){
                        castMembers.remove(castMembers.get(k));
                        k--;
                    }
                }
            }
            for (int i = 0; i < castMembers.size(); i++){
                System.out.println(increment + ". " + castMembers.get(i));
                increment++;
            }

            System.out.println("Which would you like to see all movies for? ");
            System.out.print("Enter number: ");

            ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
            int userNum = scan.nextInt();
            System.out.println();
            int x = 1;
            for (int  i = 0; i < movies.size(); i++){
                if (movies.get(i).getCast().contains(castMembers.get(userNum - 1))){
                    listOfMovies.add(movies.get(i));
                }
            }
            for (int i = 1; i < listOfMovies.size(); i++){
                Movie currentWord = listOfMovies.get(i);
                int index = i;
                while (index > 0 && currentWord.getTitle().compareTo(listOfMovies.get(index - 1).getTitle()) < 0){
                    listOfMovies.set(index, listOfMovies.get(index - 1));
                    index--;
                }
                listOfMovies.set(index, currentWord);
            }
            for (int i = 0; i < listOfMovies.size(); i++){
                System.out.println(x + ". " + listOfMovies.get(i).getTitle());
                x++;
            }

            System.out.println();
            System.out.println("Which movie would you like to learn more about? ");
            System.out.print("Enter number: ");
            int userNumMovie = scan.nextInt();
            System.out.println();
            System.out.println("Title: " + listOfMovies.get(userNumMovie - 1).getTitle());
            System.out.println("Runtime: " + listOfMovies.get(userNumMovie - 1).getRuntime() + " minutes");
            System.out.println("Directed by: " + listOfMovies.get(userNumMovie - 1).getDirector());
            System.out.println("Cast: " + listOfMovies.get(userNumMovie - 1).getCast());
            System.out.println("Overview: " + listOfMovies.get(userNumMovie - 1).getOverview());
            System.out.println("User rating: " + listOfMovies.get(userNumMovie - 1).getUserRating());
        }
    }
}
