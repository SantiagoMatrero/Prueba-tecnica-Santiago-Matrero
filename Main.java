import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        List<Book>books = instanciarLibros();

        List<Member>members = instanciarMiembros();

        List<Loan>loans = new ArrayList<>();

        while(true){

            String menu = (
            "1) Listar libros\n" +
            "2) Listar socios\n" +
            "3) Registrar prestamo\n" +
            "4) Registrar devolucion\n" +
            "5) Listar prestamos activos\n" +
            "0) Salir"
            );

            int respuesta = pedirEntradaNumerica(scanner, menu);

            switch (respuesta) {
                case 1:
                    listarLibros(books);
                    break;
                case 2:
                    listarSocios(members);
                    break;
                case 3:
                    registrarPrestamo(members, books, loans, scanner);
                    break;
                case 4:
                    registrarDevolucion(scanner, loans);
                    break;
                case 5:
                    listarPrestamosActivos(loans);
                    break;
                case 0:
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }


    public static List<Book> instanciarLibros(){

        Book B1 = new Book("TOP 10", "Alan Moore", 5);
        Book B2 = new Book("Watchmen", "Alan Moore", 4);
        Book B3 = new Book("22/11/63", "Stephen King", 7);
        Book B4 = new Book("Proyecto Hail Mary", "Andy Weir", 8);
        Book B5 = new Book("Batman The Dark Knight Returns", "Frank Miller", 1);

        List<Book>books = new ArrayList<>();

        books.add(B1);
        books.add(B2);
        books.add(B3);
        books.add(B4);
        books.add(B5);

        return books;
    }

    public static List<Member> instanciarMiembros(){
        
        
        Member M1 = new Member("Matias");
        Member M2 = new Member("Santiago");
        Member M3 = new Member("Nicolas");

        List<Member>members = new ArrayList<>();

        members.add(M1);
        members.add(M2);
        members.add(M3);

        return members;
    }

    public static void listarLibros(List<Book> books) {
        for (Book b : books) {
            System.out.println(
                "Id: " + b.getId() +
                ", Nombre: " + b.getTitle() +
                ", Autor: " + b.getAuthor() +
                ", Copias disponibles: " + b.getAvailableCopies() + "."
            );
        }
    }

    public static void listarSocios(List<Member> members) {
        for (Member m : members) {
            System.out.println(
                "Id: " + m.getId() +
                ", Nombre: " + m.getName() + "."
            );
        }
    }

    public static void registrarPrestamo(List<Member>members ,List<Book>books, List<Loan>loans, Scanner scanner){

        String solicitudIdSocio = (
            "Ingrese el id del socio solicitante."
        );

        long idSocio = pedirEntradaNumerica(scanner, solicitudIdSocio);

        Member member = buscarSocioPorId(members ,idSocio);

        if(member == null){
            System.out.println("El Id ingresado no pertenece a ningun socio.");
            return;
        };

        String solicitudIdLibro = (
            "Ingrese el id del libro solicitado."
        );

        long idLibro = pedirEntradaNumerica(scanner, solicitudIdLibro);

        Book book = buscarLibroPorId(books, idLibro);

        if(book == null){
            System.out.println("El Id ingresado no pertenece a ningun libro.");
            return;
        };

        if(!validarStock(books, idLibro)){
            System.out.println("No se puede realizar el prestamos debido a falta de copias del libro.");
            return;
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan(book, member, LocalDate.now(), false);

        loans.add(loan);

        System.out.println("Se ha registrado el prestamo con exito.");
        
    }

    public static boolean validarStock(List<Book>books, long idLibro){

        for(Book b : books){
            if(b.getId() == idLibro){
                if(b.getAvailableCopies() == 0){
                    return false;
                }
            }
        }
        return true;
    }


    // Repeticion de codigo, pense en arreglarlo con una interface pero no me termino de cerrar.

    public static Book buscarLibroPorId(List<Book>books, long idLibro){
        for (Book b : books) {
            if (b.getId() == idLibro) {
                return b;
            }
        }
        return null;
    }

    public static Member buscarSocioPorId(List<Member>members, long idSocio){
        for (Member m : members) {
            if (m.getId() == idSocio) {
                return m;
            }
        }
        return null;
    }

    public static Loan buscarPrestamoPorId(List<Loan>loans, long idLoan){
        for (Loan l : loans) {
            if (l.getId() == idLoan) {
                return l;
            }
        }
        return null;
    }

    public static void registrarDevolucion(Scanner scanner, List<Loan>loans){
     
        String pedidoIdPrestamo = (
            "Ingrese el id del prestamo."
        );

        long idPrestamo = pedirEntradaNumerica(scanner, pedidoIdPrestamo);

        Loan loan = buscarPrestamoPorId(loans ,idPrestamo);

        if(loan == null){
            System.out.println("El Id ingresado no pertenece a ningun prestamo.");
            return;
        };

        if(loan.getReturned() == true){
            System.out.println("Este prestamo ya fue devuelto anteriormente.");
            return;
        }

        loan.setReturned(true);

        Book book = loan.getBook();

        book.setAvailableCopies(book.getAvailableCopies()+1);

        System.out.println("Se ha registrado la devolucion con exito.");

    }

    public static void listarPrestamosActivos(List<Loan>loans){

        boolean hayPrestamosActivos = false;

        for (Loan l : loans) {
            if (!l.getReturned()) {
                hayPrestamosActivos = true;
                System.out.println(
                "Id: " + l.getId() +
                ", Titulo: " + l.getBook().getTitle() + 
                ", Solicitante: " + l.getMember().getName() +
                ", Fecha de prestamo: " + l.getLoanDate() + "."
                );
            }
        }

        if(hayPrestamosActivos == false){
            System.out.println("No hay prestamos activos.");
        }

    }

    public static int pedirEntradaNumerica(Scanner scanner, String menu){
        while(true){
            System.out.println(menu);

            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Opcion invalida. La entrada debe ser un numero.");
                scanner.nextLine();
            }

        }
    }


}

class Book {
    private static long contadorId = 0;
    private long id;
    private String title;
    private String author;
    private int availableCopies;

    public Book (String title, String author, int availableCopies){
        this.id = contadorId++;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public int getAvailableCopies(){
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies){
        this.availableCopies = availableCopies;
    }

}

class Member {
    private static long contadorId = 0;
    private long id;
    private String name;

    public Member (String name){
        this.id = contadorId++;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }


}

class Loan {
    private static long contadorId = 0;
    private long id;
    private Book book;
    private Member member;
    private LocalDate loanDate;
    private boolean returned;

    public Loan (Book book, Member member, LocalDate loanDate, boolean returned){
        this.id = contadorId++;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.returned = returned;
    }

    public long getId(){
        return this.id;
    }

    public boolean getReturned(){
        return this.returned;
    }

    public Book getBook(){
        return this.book;
    }

    public Member getMember(){
        return this.member;
    }

    public LocalDate getLoanDate(){
        return this.loanDate;
    }

    public void setReturned(Boolean bool){
        returned = bool;
    }
}