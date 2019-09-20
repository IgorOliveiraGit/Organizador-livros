package Applications;

import Entities.Produtos;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Produtos> lista = new ArrayList<>();

        if(load() != null){
            lista = load();
        }

        int x = 1;
        String nome;
        double soma = 0;
        double media = 0;

        while(x != 0){
            System.out.println("1. Adicionar produto a lista:");
            System.out.println("2. Remover produto da lista:");
            System.out.println("3. Editar produto da lista:");
            System.out.println("4. Listar produtos:");
            System.out.println("5. Listar produtos por editora:");
            System.out.println("6. Listar produtos por datas:");
            System.out.println("7. Valor total gasto:");
            System.out.println("8. Valor total gasto por editora:");
            System.out.println("9. Salvar:");
            System.out.println("0. Salvar e Sair.");
            x = parseInteger(sc);
            switch (x){
                case 1:
                    clearScreen();
                    Produtos produtos = adicionar(sc, sdf);
                    if(produtos != null){
                        lista.add(produtos);
                    }
                break;
                case 2:
                    sc.nextLine();
                    clearScreen();
                    System.out.println("Digite o nome para remover:");
                    nome = sc.nextLine();
                    for (Produtos p : lista) {
                        if(p.getNome().compareToIgnoreCase(nome) == 0){
                            lista.remove(p);
                            break;
                        }
                    }
                break;
                case 3:
                    sc.nextLine();
                    clearScreen();
                    System.out.println("Digite o nome para editar:");
                    nome = sc.nextLine();
                    for (Produtos p : lista) {
                        if(p.getNome().compareToIgnoreCase(nome) == 0){
                            System.out.println("1. Editar nome:");
                            System.out.println("2. Editar editora:");
                            System.out.println("3. Editar valor:");
                            System.out.println("4. Editar data:");

                            int y = parseInteger(sc);
                            switch (y){
                                case 1:
                                    clearScreen();
                                    System.out.println("Digite o novo nome:");
                                    sc.nextLine();
                                    p.setNome(sc.nextLine());
                                    System.out.println("nome trocado com sucesso");
                                    break;
                                case 2:
                                    clearScreen();
                                    System.out.println("Digite o novo nome da editora:");
                                    sc.nextLine();
                                    p.setEditora(sc.nextLine());
                                    break;
                                case 3:
                                    clearScreen();
                                    System.out.println("Digite o novo valor:");
                                    sc.nextLine();
                                    p.setValor(parseDouble(sc));
                                    break;
                                case 4:
                                    clearScreen();
                                    System.out.println("Digite a nova data:");
                                    Date data = parseDate(sc, sdf);
                                    p.setData(data);

                                    break;
                                default:
                                    System.out.println("Valor invalido.");
                                    break;
                            }
                            break;
                        }
                    }
                break;

                case 4:
                    clearScreen();
                    for (Produtos p: lista) {
                        System.out.println(p);
                    }
                    System.out.println("Pressione enter para continuar.");
                    sc.nextLine();
                    sc.nextLine();
                break;

                case 5:
                    clearScreen();
                    sc.nextLine();
                    System.out.println("Digite o nome da editora para buscar:");
                    String nomeEditora = sc.nextLine();
                    for (Produtos p: lista) {
                        if(p.getEditora().toLowerCase().contains(nomeEditora)){
                            System.out.println(p);
                        }
                    }
                    System.out.println("Pressione enter para continuar.");
                    sc.nextLine();
                    break;

                case 6:
                    clearScreen();
                    sc.nextLine();
                    System.out.println("Digite a data inicial a ser calculada:");
                    Date dataInicial = parseDate(sc, sdf);

                    System.out.println("Digite a data final a ser calculada:");
                    Date dataFinal = parseDate(sc, sdf);

                    for (Produtos p: lista) {
                        if(isWithinRange(dataInicial, dataFinal, p.getData())){
                            System.out.println(p);
                        }
                    }
                    System.out.println("Pressione enter para continuar.");
                    sc.nextLine();
                    sc.nextLine();
                    break;

                case 7:
                    clearScreen();
                    soma = 0;
                    media = 0;
                    for (Produtos p: lista) {
                        soma += p.getValor();
                    }
                    media = soma/lista.size();

                    System.out.printf("\nValor total: R$ %.2f\n", soma);
                    System.out.printf("Media por livro: R$ %.2f\n", media);
                    System.out.println("Total de livros comprados: " + lista.size() + "\n\n");
                    System.out.println("Pressione enter para continuar.");
                    sc.nextLine();
                    sc.nextLine();
                    break;

                case 8:
                    clearScreen();
                    sc.nextLine();
                    soma = 0;
                    media = 0;
                    int i = 0;
                    System.out.println("Digite o nome da editora para buscar:");
                    nomeEditora = sc.nextLine();
                    for (Produtos p: lista) {
                        if(p.getEditora().toLowerCase().contains(nomeEditora)){
                            soma += p.getValor();
                            i++;
                        }
                    }
                    media = soma/i;
                    System.out.printf("\nValor total: R$ %.2f\n", soma);
                    System.out.printf("Media por livro: R$ %.2f\n", media);
                    System.out.printf("Total de livros comprados: %d\n\n", i);
                    System.out.println("Pressione enter para continuar.");
                    sc.nextLine();
                    break;

                case 9:
                    clearScreen();
                    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("texto.txt"));
                    output.writeObject(lista);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("numero invalido;");
                break;
            }
            clearScreen();
        }

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("texto.txt"));
        output.writeObject(lista);

    }

    public static Produtos adicionar(Scanner sc, SimpleDateFormat sdf) {
        sc.nextLine();
        System.out.println("Digite o nome do produto:");
        String nome = sc.nextLine();
        System.out.println("Digite o nome da editora:");
        String editora = sc.nextLine();
        System.out.println("Digite o valor do produto:");
        double valor = parseDouble(sc);
        System.out.println("Digite a data da compra:");

        Date data = parseDate(sc, sdf);

        if(valor >= 0){
            return new Produtos(nome, valor, data, editora);
        }else{
            return new Produtos(nome, 0, data, editora);
        }
    }

    public static Date parseDate(Scanner sc, SimpleDateFormat sdf){
        Date data = null;
        boolean correto = false;
        while(!correto){
            try {
                data = sdf.parse(sc.next());
                correto = true;
            }catch (ParseException e){
                System.out.println("data invalida");
                 correto = false;
            }
        }
        return data;
    }

    public static ArrayList load() throws IOException, ClassNotFoundException {
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(new FileInputStream("texto.txt"));
        }catch (FileNotFoundException e){
            System.out.println("Impossivel ler dados.");
            return null;
        }

        List<Produtos> lista;

        try {
            lista = (ArrayList) input.readObject();
        } catch (IOException e) {
            System.out.println("Impossivel ler dados.");
            return null;
        }

        return (ArrayList) lista;
    }

    public static Double parseDouble(Scanner sc){
        while(!sc.hasNextDouble()){
           sc.nextLine();
            System.out.println("Tipo de dado inválido. Digite novamente:");
        }
        double valor = sc.nextDouble();

        return valor;
    }
    public static Integer parseInteger(Scanner sc){
        while(!sc.hasNextInt()){
            sc.nextLine();
            System.out.println("Tipo de dado inválido. Digite novamente:");
        }
        int valor = sc.nextInt();

        return valor;
    }

    public static void clearScreen() {
        final String operatingSystem = System.getProperty("os.name");
        try{
            if (operatingSystem .contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }catch (IOException e){
            System.out.println("Impossivel limpar tela.");
        }
    }

    public static boolean isWithinRange(Date startDate, Date endDate, Date testDate) {
        if (startDate.equals(testDate) || endDate.equals(testDate)){
            return true;
        }
        return testDate.after(startDate) && testDate.before(endDate);
    }

}
