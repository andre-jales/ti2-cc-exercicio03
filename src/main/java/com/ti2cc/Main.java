package com.ti2cc;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		PessoaDAO dao = new PessoaDAO();
        dao.conectar();

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1) Inserir");
            System.out.println("2) Listar");
            System.out.println("3) Atualizar");
            System.out.println("4) Excluir");
            System.out.println("5) Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n=== Inserir ===");
                    System.out.print("Digite o id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a idade: ");
                    int idade = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();

                    Pessoa pessoa = new Pessoa(id, nome, idade, email);
                    if (dao.inserirPessoa(pessoa)) {
                        System.out.println("Inserção com sucesso: " + pessoa);
                    } else {
                        System.out.println("Erro ao inserir pessoa.");
                    }
                    break;
                case 2:
                    System.out.println("\n=== Listar ===");
                    Pessoa[] pessoas = dao.getPessoas();
                    for (Pessoa p : pessoas) {
                        System.out.println(p);
                    }
                    break;
                case 3:
                    System.out.println("\n=== Atualizar ===");
                    System.out.print("Digite o id da pessoa a ser atualizada: ");
                    int codigoAtualizacao = scanner.nextInt();
                    scanner.nextLine();
                    
                    Pessoa pessoaAtualizar = dao.getPessoaById(codigoAtualizacao);

                    if (pessoaAtualizar != null) {
                        System.out.print("Digite o novo email: ");
                        String novoEmail = scanner.nextLine();
                        pessoaAtualizar.setEmail(novoEmail);

                        if (dao.atualizarPessoa(pessoaAtualizar)) {
                            System.out.println("Atualização realizada com sucesso.");
                        } else {
                            System.out.println("Erro ao atualizar pessoa.");
                        }
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    break;
                case 4:
                    System.out.println("\n=== Excluir ===");
                    System.out.print("Digite o id da pessoa a ser excluída: ");
                    int idExclusao = scanner.nextInt();
                    if (dao.excluirPessoa(idExclusao)) {
                        System.out.println("Pessoa excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir pessoa.");
                    }
                    break;
                case 5:
                    System.out.println("\nSaindo...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Escolha novamente.");
            }

        } while (opcao != 5);

        dao.close();
        scanner.close();
	}

}