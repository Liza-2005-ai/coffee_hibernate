package com.coffeeshop.service;

import com.coffeeshop.entity.*;
import com.coffeeshop.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Демонстрация CRUD-операций через Hibernate Repository
 */
public class CrudDemoService {

    private final ClientRepository clientRepo = new ClientRepository();
    private final RateRepository rateRepo = new RateRepository();
    private final SubscriptionRepository subscriptionRepo = new SubscriptionRepository();
    private final PackageRepository packageRepo = new PackageRepository();
    private final GrainVarietyRepository grainVarietyRepo = new GrainVarietyRepository();
    private final RoastingProfileRepository roastingProfileRepo = new RoastingProfileRepository();
    private final FinishedProductRepository finishedProductRepo = new FinishedProductRepository();
    private final GenericRepository<TasteDescriptor, Integer> tasteDescriptorRepo =
            new GenericRepository<>(TasteDescriptor.class);

    //CREATE

    public void demoCreate() {
        System.out.println("\nCreate:");

        Client client = clientRepo.save(new Client(
                "Тест", "Тестов", "Тестович", "test_demo@test.ru", "+79990000000", "Тестовый адрес"));
        System.out.printf("Создан клиент: id=%d, %s %s%n", client.getId(), client.getSurname(), client.getName());

        Rate rate = rateRepo.save(new Rate(BigDecimal.valueOf(0.250), BigDecimal.valueOf(599.00), 14));
        System.out.printf("Создан тариф: id=%d, объём=%s кг, цена=%s%n", rate.getId(), rate.getVolume(), rate.getPrice());

        Subscription subscription = subscriptionRepo.save(
                new Subscription(LocalDateTime.now(), "not_active", client, rate));
        System.out.printf("Создана подписка: id=%d, статус='%s'%n", subscription.getId(), subscription.getStatus());
    }

    //READ

    public void demoRead() {
        System.out.println("\nRead:");

        System.out.println("Все клиенты:");
        for (Client c : clientRepo.findAll()) {
            System.out.printf("  id=%d, %s %s, тел=%s, email=%s%n",
                    c.getId(), c.getSurname(), c.getName(), c.getNumber(), c.getEmailAddress());
        }

        System.out.println("Все тарифы:");
        for (Rate r : rateRepo.findAll()) {
            System.out.printf("  id=%d, объём=%s кг, цена=%s, период=%d дн%n",
                    r.getId(), r.getVolume(), r.getPrice(), r.getFrequency());
        }

        System.out.println("Готовая продукция:");
        for (FinishedProduct p : finishedProductRepo.findAll()) {
            System.out.printf("  id=%d, %s, вес=%s кг, цена=%s%n",
                    p.getId(), p.getName(), p.getWeight(), p.getPrice());
        }

        System.out.println("Поиск клиента по id=1:");
        clientRepo.findById(1).ifPresentOrElse(
                c -> System.out.println("  " + c),
                () -> System.out.println("  Не найден"));
    }

    //UPDATE

    public void demoUpdate() {
        System.out.println("\nUpdate:");

        clientRepo.findById(1).ifPresent(c -> {
            String oldAddress = c.getAddress();
            c.setAddress("Москва, ул. Новая, 99");
            Client updated = clientRepo.update(c);
            System.out.printf("Обновлён адрес клиента id=1: '%s' -> '%s'%n", oldAddress, updated.getAddress());
            updated.setAddress(oldAddress);
            clientRepo.update(updated);
        });

        packageRepo.findById(1).ifPresent(p -> {
            String oldStatus = p.getStatus();
            boolean ok = packageRepo.updateStatus(1, "shipped");
            System.out.printf("Обновлён статус посылки id=1: '%s' -> 'shipped' (успех=%b)%n", oldStatus, ok);
            packageRepo.updateStatus(1, oldStatus);
        });
    }

    //DELETE

    public void demoDelete() {
        System.out.println("\nDelete:");

        Client temp = clientRepo.save(new Client(
                "Удали", "Меня", "Тестовна", "delete_demo@test.ru", "+70000000001", null));
        System.out.printf("Создан временный клиент: id=%d%n", temp.getId());

        boolean deleted = clientRepo.deleteById(temp.getId());
        System.out.printf("Удалён клиент id=%d (успех=%b)%n", temp.getId(), deleted);

        boolean notFound = clientRepo.deleteById(999999);
        System.out.printf("Удаление несуществующего id=999999 (успех=%b)%n", notFound);
    }

    //BATCH INSERT

    public void demoBatchInsert() {
        System.out.println("\nBatch insert:");

        GrainVariety variety = grainVarietyRepo.findAll().get(0);
        RoastingProfile profile = roastingProfileRepo.findAll().get(0);

        List<FinishedProduct> products = new java.util.ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            products.add(new FinishedProduct(
                    BigDecimal.valueOf(0.250 * i),
                    BigDecimal.valueOf(500 + i * 50),
                    "Demo Batch Product " + i,
                    variety, profile));
        }

        long start = System.nanoTime();
        int count = finishedProductRepo.batchInsert(products);
        long elapsed = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("Вставлено %d продуктов за %d мс (batch)%n", count, elapsed);

        for (FinishedProduct p : products) {
            finishedProductRepo.deleteById(p.getId());
        }
        System.out.println("Тестовые продукты удалены");
    }

    //TRANSACTION

    public void demoTransaction() {
        System.out.println("\nТранзакция (активация подписки + создание посылки):");

        Client client = clientRepo.findById(1).orElseThrow();
        Rate rate = rateRepo.findAll().get(0);
        Subscription subscription = subscriptionRepo.save(
                new Subscription(LocalDateTime.now(), "not_active", client, rate));
        System.out.printf("Создана тестовая подписка id=%d (статус 'not_active')%n", subscription.getId());

        try {
            int packageId = subscriptionRepo.activateSubscriptionWithFirstPackage(subscription.getId(), "TRK-DEMO-001");
            System.out.printf("Транзакция успешна: подписка активирована, создана посылка id=%d%n", packageId);

            packageRepo.deleteById(packageId);
            subscriptionRepo.deleteById(subscription.getId());
            System.out.println("Тестовые данные транзакции удалены");
        } catch (Exception e) {
            System.out.println("Транзакция отменена (rollback): " + e.getMessage());
        }
    }

    //Работа со связью M:N (сорт зерна вкусовые дескрипторы)

    public void demoGrainVarietyTasteWork() {
        System.out.println("\nВкусовые дескрипторы сорта зерна (M:N):");

        GrainVariety variety = grainVarietyRepo.save(new GrainVariety("Demo Geisha"));
        System.out.printf("Создан сорт зерна: id=%d, '%s'%n", variety.getId(), variety.getName());

        TasteDescriptor descriptor = tasteDescriptorRepo.save(new TasteDescriptor("Жасмин", "вкус"));
        grainVarietyRepo.addTasteDescriptor(variety.getId(), descriptor.getId());

        GrainVariety withDescriptors = grainVarietyRepo.findByIdWithTasteDescriptors(variety.getId()).orElseThrow();
        System.out.println("Вкусовые дескрипторы сорта: " +
                withDescriptors.getTasteDescriptors().stream().map(TasteDescriptor::getName).toList());

        grainVarietyRepo.removeTasteDescriptor(variety.getId(), descriptor.getId());
        grainVarietyRepo.deleteById(variety.getId());
        tasteDescriptorRepo.deleteById(descriptor.getId());
        System.out.println("Тестовые данные удалены");
    }

    //Поиск клиента по email

    public void demoFindByEmail() {
        System.out.println("\nПоиск клиента по email:");

        String email = "ivanov@mail.ru";
        Optional<Client> clientOpt = clientRepo.findByEmail(email);

        if (clientOpt.isPresent()) {
            Client c = clientOpt.get();
            System.out.println("Найден клиент:");
            System.out.println("  ID: " + c.getId());
            System.out.println("  ФИО: " + c.getSurname() + " " + c.getName() + " " + c.getMiddleName());
            System.out.println("  Телефон: " + c.getNumber());
            System.out.println("  Email: " + c.getEmailAddress());
        } else {
            System.out.println("Клиент с email '" + email + "' не найден");
        }
    }

    public void runAll() {
        demoRead();
        demoCreate();
        demoUpdate();
        demoDelete();
        demoBatchInsert();
        demoTransaction();
        demoGrainVarietyTasteWork();
        demoFindByEmail();
    }
}
