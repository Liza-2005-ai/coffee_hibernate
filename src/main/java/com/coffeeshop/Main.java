package com.coffeeshop;

import com.coffeeshop.service.BusinessQueryService;
import com.coffeeshop.service.CrudDemoService;
import com.coffeeshop.util.DataSeeder;
import com.coffeeshop.util.HibernateUtil;

import java.util.Scanner;

public class Main {

    private static final CrudDemoService crudDemo = new CrudDemoService();
    private static final BusinessQueryService bizQuery = new BusinessQueryService();

    public static void main(String[] args) {
        System.out.println("=== Hibernate Coffee Shop Demo (Java 17 · PostgreSQL · Hibernate/JPA) ===\n");

        try {
            // Инициализация EntityManagerFactory
            HibernateUtil.getEntityManagerFactory();
            DataSeeder.seed();
            System.out.println("БД готова.\n");
        } catch (Exception e) {
            System.err.println("Ошибка инициализации: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1"  -> crudDemo.demoCreate();
                    case "2"  -> crudDemo.demoRead();
                    case "3"  -> crudDemo.demoUpdate();
                    case "4"  -> crudDemo.demoDelete();
                    case "5"  -> crudDemo.demoBatchInsert();
                    case "6"  -> crudDemo.demoTransaction();
                    case "7"  -> crudDemo.demoGrainVarietyTasteWork();
                    case "8"  -> crudDemo.demoFindByEmail();
                    case "9"  -> bizQuery.productsWithoutReviews();
                    case "10" -> bizQuery.revenueByRate();
                    case "11" -> bizQuery.topClientsByPackages();
                    case "12" -> bizQuery.avgGradeByProduct();
                    case "13" -> bizQuery.farmersWithPlantations();
                    case "14" -> {
                        crudDemo.runAll();
                        bizQuery.runAll();
                    }
                    case "0"  -> running = false;
                    default   -> System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("До свидания!");
        HibernateUtil.close();
    }

    private static void printMenu() {
        System.out.println("""

                --- CRUD (Hibernate/JPA) ---
                1.  Создать клиента, тариф, подписку
                2.  Показать всех клиентов, тарифы, продукцию
                3.  Обновить адрес клиента и статус посылки
                4.  Удалить тестового клиента
                5.  Batch insert готовой продукции
                6.  Транзакция: активировать подписку + создать посылку
                7.  Вкусовые дескрипторы сорта зерна (M:N)
                8.  Найти клиента по email

                --- Бизнес-запросы (JPQL) ---
                9.  Продукты без отзывов
                10. Подписчики и выручка по тарифам
                11. Топ-3 клиента по количеству посылок
                12. Средний рейтинг отзывов по продуктам
                13. Фермеры, у которых есть плантации

                14. Запустить всё
                0.  Выход""");
    }
}
