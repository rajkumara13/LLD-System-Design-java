package org.example;

import java.net.SocketOption;
import java.util.*;

class User{
    String name;
    List<Books> list;
    User(String name){
        this.name=name;
        this.list=new ArrayList<>();
    }
}
class Books{
    String title;
    String author;
    int count;
    boolean available;
    Books(String title,String author,int count){
        this.title=title;
        this.author=author;
        this.count=count;
        this.available=count>0;
    }
}
class LibManagementSys{
    List<Books>booksList;
    List<User>userList;
    LibManagementSys(){
        this.booksList=new ArrayList<>();
        this.userList=new ArrayList<>();
    }
    public void addMem(User user){
        for (User u : userList) {
            if (u.name.equalsIgnoreCase(user.name)) {
                System.out.println("Member already exists: " + user.name);
                return;
            }
        }

        userList.add(user);
        System.out.println("Member added  "+user.name);
        return;
    }
    public void addbook(Books book){
        for (Books b : booksList) {

            if (b.title.equalsIgnoreCase(book.title)) {

                b.count += book.count;
                b.available = b.count > 0;

                System.out.println(
                        "Book already exists. Updated count: " + b.count
                );

                return;
            }
        }
        booksList.add(book);
        System.out.println("Books add successfully   "+book.title);
    }
    public void addBooks(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();
        System.out.println("Book count :");
        int count=sc.nextInt();

        for(Books b1:booksList) {
            if (b1.title.equalsIgnoreCase(title)) {
                b1.count+= count;
                b1.available=b1.count>0;
                System.out.println("The Book " + b1.title + " is Already available so Add count :" + b1.count);
                return;
            }
        }
            Books b=new Books(title,author,count);
            booksList.add(b);
            System.out.println("The Books added Succesfully  "+b.title);
            return;
    }
    public void updateBook(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter book Name :");
        String oldBook=sc.nextLine();
        System.out.println("Enter new Book name :");
        String newTitle=sc.nextLine();
        System.out.println("Enter new Author Name :");
        String newAuthor=sc.nextLine();

        for(Books b:booksList){
            if(b.title.equalsIgnoreCase(oldBook)){
                b.title=newTitle;
                b.author=newAuthor;
                System.out.println("updated Successfully");
                return;
            }
        }
        System.out.println("not found book");
        return ;
    }
    public void removeBookCopy(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();


        for(Books b1:booksList){
            if(b1.title.equalsIgnoreCase(title) && b1.count!=0){
                b1.count--;
                if (b1.count == 0) {
                    b1.available = false;
                }
                System.out.println("Book : " + title + " remove succesfull");
                return;
            }
        }
        System.out.println("Book :"+title+"Not Available");
    }
    public boolean isAvailable(String title){
        for(Books b1:booksList){
            if(b1.title.equalsIgnoreCase(title) && b1.count>=1){
                return true;
            }
        }
        return false;
    }
    public void addMember(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your Name :");
        String name=sc.nextLine();
        for(User user:userList) {
            if (user.name.equalsIgnoreCase(name)) {
                System.out.println("Already Exist this name :" + name);
                return;
            }
        }
            User user1=new User(name);
            userList.add(user1);
            System.out.println("Member add successfully :"+name);
            return;
    }
    public void displayAllMembers(){
        for(User u1:userList){
            System.out.println("Name :"+u1.name);
        }
    }

    public void displayAll(){
        for(Books b:booksList){
            System.out.println("Title :"+b.title+", Author :"+b.author+", Books Count :"+b.count);
        }
    }
    public boolean userFinder(String name){
        for(User user:userList){
            if(user.name.equalsIgnoreCase(name)){
                return true;
            }
        }
        User newUser=new User(name);
        userList.add(newUser);
        System.out.println("new user added "+name);
        return true;
    }
    public void borrowBook(String name){
        User user=null;
        for(User u1:userList){
            if(u1.name.equalsIgnoreCase(name)){
                user=u1;
                break;
            }
        }
        if (user == null) {
            System.out.println("User is not found");
            return;
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Book Name  "+ user.name);
        String title=sc.nextLine();
        if(user.list.size()<5 ){
            for (Books b : booksList) {
                if (b.title.equalsIgnoreCase(title)) {
                    if (isAvailable(title)) {
                        b.count--;
                        if (b.count == 0) {
                            b.available = false;
                        }

                        user.list.add(b);
                        System.out.println("Book borrowed Successfully");
                        return;
                    } else {
                        System.out.println("the book is not Available " + title);
                        return;
                    }
                }
            }
            System.out.println("The book is not found");
            return;
        }
        System.out.println("user is already 5 books having");
        return;
    }
    public void returnBook(String name){
        User user=null;
        for(User u1:userList){
            if(u1.name.equalsIgnoreCase(name)){
                user=u1;
                break;
            }
        }
        if (user == null) {
            System.out.println("User is not found");
            return;
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Book Name  "+ user.name);
        String title=sc.nextLine();
        for(Books b:booksList){
            if(b.title.equalsIgnoreCase(title)){
                if(user.list.contains(b)){
                    b.count++;
                    if (b.count > 0) {
                        b.available = true;
                    }
                    user.list.remove(b);
                    System.out.println("Book return Successfully");
                    return;
                }
                else{
                    System.out.println("You have not borrowed this book  "+title);
                    return ;
                }
            }
        }
        System.out.println("The book is not have");
        return;
    }
}
class Main {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        LibManagementSys lib1=new LibManagementSys();
        User u1=new User("rajkumar");
        lib1.addMem(u1);
        User u2=new User("kumar");
        lib1.addMem(u2);
        Books b1=new Books("maths","math1",2);
        lib1.addbook(b1);
        Books b2=new Books("sci","sci2",3);
        lib1.addbook(b2);
        System.out.println("Enter role (admin/user) :");
        String role = sc.nextLine();
        if (role.equalsIgnoreCase("admin")) {
            System.out.println("Enter username: ");
            String username = sc.nextLine();
            System.out.println("Enter Password : ");
            String password = sc.nextLine();

            if (username.equalsIgnoreCase("admin123") && password.equalsIgnoreCase("pass123")) {
                adminMenu(lib1);
            }
        } else if (role.equalsIgnoreCase("user")) {
            System.out.println("Enter username: ");
            String username = sc.nextLine();
            System.out.println("Enter Password : ");
            String password = sc.nextLine();
            if (username.equalsIgnoreCase("user123") && password.equalsIgnoreCase("pass123")) {
                userMenu(lib1);
            }
        } else {
            System.out.println("Not invalid option");
        }

    }

    public static void adminMenu(LibManagementSys lib) {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Member");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Members");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    lib.addBooks();
                    break;
                case 2:
                    lib.updateBook();
                    break;
                case 3:
                    lib.removeBookCopy();
                    break;
                case 4:
                    lib.addMember();
                    break;
                case 5:
                    lib.displayAll();
                    break;
                case 6:
                    lib.displayAllMembers();
                    break;
                case 7:
                    return;
            }
        }
    }

    public static void userMenu(LibManagementSys lib) {
        System.out.println("Enter your name :");
        String name = sc.nextLine();
        boolean found = lib.userFinder(name);
        if (lib.userFinder(name)) {
            System.out.println("Welcome " + name);
            while (true) {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Borrow Book");
                System.out.println("2. Return Book");
                System.out.println("3. DisplayAll Books");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        lib.borrowBook(name);
                        break;
                    case 2:
                        lib.returnBook(name);
                        break;
                    case 3:
                        lib.displayAll();
                        break;
                    case 4:
                        return;
                }
            }
        }
    }
}
