package com.coffeeshop.util;

import com.coffeeshop.entity.*;
import com.coffeeshop.entity.Package; // явный импорт: иначе компилятор путает с java.lang.Package
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Заполняет базу тестовыми данными при первом запуске (если она пуста).
 * Данные взяты из той же JDBC-версии проекта (SchemaInitializer), чтобы
 * демонстрация была наглядной и совпадала по смыслу с твоей исходной БД.
 *
 * Порядок вставки важен: сначала сущности без внешних ключей (Client, Rate,
 * Farmer, GrainVariety, RoastingProfile, TasteDescriptor), затем те, что
 * на них ссылаются (Plantation, FinishedProduct, Subscription), и в конце —
 * самые "глубокие" (Package, Review, PackageComposition, ReviewItem).
 */
public final class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private DataSeeder() {}

    public static void seed() {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Long clientsCount = em.createQuery("SELECT COUNT(c) FROM Client c", Long.class).getSingleResult();
            if (clientsCount > 0) {
                tx.commit();
                log.info("Начальные данные уже есть, заполнение пропущено");
                return;
            }

            // ---- Клиенты ----
            Client c1 = new Client("Иван", "Иванов", "Иванович", "ivanov@mail.ru", "+79001234567", "Москва, ул. Ленина 1");
            Client c2 = new Client("Анна", "Петрова", "Сергеевна", "petrova@gmail.com", "+79009876543", "СПб, Невский пр. 10");
            Client c3 = new Client("Пётр", "Сидоров", "Алексеевич", "sidorov@yandex.ru", "+79005551234", "Казань, ул. Баумана 5");
            Client c4 = new Client("Мария", "Козлова", "Игоревна", "kozlova@mail.ru", "+79003334455", "Екатеринбург, ул. Малышева 20");
            Client c5 = new Client("Алексей", "Волков", "Дмитриевич", "volkov@gmail.com", "+79007778899", "Новосибирск, Красный пр. 7");
            Client c6 = new Client("Елена", "Новикова", "Олеговна", "novikova@mail.ru", "+79002223344", "Москва, ул. Тверская 15");
            Client c7 = new Client("Дмитрий", "Морозов", "Викторович", "morozov@yandex.ru", "+79006665577", "СПб, Лиговский пр. 30");
            Client c8 = new Client("Ольга", "Соколова", "Андреевна", "sokolova@gmail.com", "+79001112233", "Казань, ул. Кремлёвская 3");
            List<Client> clients = List.of(c1, c2, c3, c4, c5, c6, c7, c8);
            clients.forEach(em::persist);

            // ---- Тарифы ----
            Rate r1 = new Rate(new BigDecimal("0.250"), new BigDecimal("590.00"), 14);
            Rate r2 = new Rate(new BigDecimal("0.500"), new BigDecimal("990.00"), 14);
            Rate r3 = new Rate(new BigDecimal("0.250"), new BigDecimal("550.00"), 30);
            Rate r4 = new Rate(new BigDecimal("1.000"), new BigDecimal("1790.00"), 30);
            Rate r5 = new Rate(new BigDecimal("0.500"), new BigDecimal("890.00"), 7);
            List<Rate> rates = List.of(r1, r2, r3, r4, r5);
            rates.forEach(em::persist);

            // ---- Фермеры ----
            Farmer f1 = new Farmer("Эфиопия", "ET-001", "Abebe Bekele");
            Farmer f2 = new Farmer("Колумбия", "CO-002", "Carlos Ramirez");
            Farmer f3 = new Farmer("Бразилия", "BR-003", "Joao Silva");
            Farmer f4 = new Farmer("Кения", "KE-004", "Wanjiru Kamau");
            List.of(f1, f2, f3, f4).forEach(em::persist);

            // ---- Плантации ----
            Plantation p1 = new Plantation("Сидамо", "Yirgacheffe Farm", f1);
            Plantation p2 = new Plantation("Уэйла", "Guji Highland", f1);
            Plantation p3 = new Plantation("Уила", "Finca El Paraiso", f2);
            Plantation p4 = new Plantation("Минас-Жерайс", "Fazenda Santa Ines", f3);
            Plantation p5 = new Plantation("Нанди", "Kamau Estate", f4);
            List.of(p1, p2, p3, p4, p5).forEach(em::persist);

            // ---- Сорта зерна ----
            GrainVariety v1 = new GrainVariety("Arabica Typica");
            GrainVariety v2 = new GrainVariety("Arabica Bourbon");
            GrainVariety v3 = new GrainVariety("Arabica Heirloom");
            GrainVariety v4 = new GrainVariety("Robusta");
            GrainVariety v5 = new GrainVariety("Arabica Caturra");
            List<GrainVariety> varieties = List.of(v1, v2, v3, v4, v5);
            varieties.forEach(em::persist);

            // ---- Вкусовые дескрипторы ----
            TasteDescriptor td1 = new TasteDescriptor("Шоколад", "вкус");
            TasteDescriptor td2 = new TasteDescriptor("Цитрус", "вкус");
            TasteDescriptor td3 = new TasteDescriptor("Орех", "вкус");
            TasteDescriptor td4 = new TasteDescriptor("Ягоды", "вкус");
            TasteDescriptor td5 = new TasteDescriptor("Карамель", "вкус");
            TasteDescriptor td6 = new TasteDescriptor("Высокая кислотность", "кислотность");
            TasteDescriptor td7 = new TasteDescriptor("Низкая кислотность", "кислотность");
            TasteDescriptor td8 = new TasteDescriptor("Лёгкое тело", "плотность");
            TasteDescriptor td9 = new TasteDescriptor("Плотное тело", "плотность");
            List.of(td1, td2, td3, td4, td5, td6, td7, td8, td9).forEach(em::persist);

            // ---- M:N сорт <-> вкус (taste_of_the_variety) ----
            v1.addTasteDescriptor(td2); v1.addTasteDescriptor(td6);                 // Typica: Цитрус, Высокая кислотность
            v2.addTasteDescriptor(td1); v2.addTasteDescriptor(td5); v2.addTasteDescriptor(td9); // Bourbon: Шоколад, Карамель, Плотное тело
            v3.addTasteDescriptor(td4); v3.addTasteDescriptor(td6);                 // Heirloom: Ягоды, Высокая кислотность
            v4.addTasteDescriptor(td3); v4.addTasteDescriptor(td7); v4.addTasteDescriptor(td9); // Robusta: Орех, Низкая кислотность, Плотное тело
            v5.addTasteDescriptor(td1); v5.addTasteDescriptor(td8);                 // Caturra: Шоколад, Лёгкое тело

            // ---- M:N сорт <-> плантация (plantation_varieties) ----
            v1.addPlantation(p1); v1.addPlantation(p2);
            v3.addPlantation(p1);
            v2.addPlantation(p3); v2.addPlantation(p5);
            v4.addPlantation(p4);
            v5.addPlantation(p4);

            // ---- Профили обжарки ----
            RoastingProfile rp1 = new RoastingProfile(new BigDecimal("195.00"), LocalTime.of(0, 8), "Светлая обжарка");
            RoastingProfile rp2 = new RoastingProfile(new BigDecimal("205.00"), LocalTime.of(0, 10), "Средняя обжарка");
            RoastingProfile rp3 = new RoastingProfile(new BigDecimal("220.00"), LocalTime.of(0, 12), "Тёмная обжарка");
            RoastingProfile rp4 = new RoastingProfile(new BigDecimal("210.00"), LocalTime.of(0, 11), "Венская обжарка");
            List.of(rp1, rp2, rp3, rp4).forEach(em::persist);

            // ---- Готовая продукция ----
            FinishedProduct fp1 = new FinishedProduct(new BigDecimal("0.250"), new BigDecimal("590.00"), "Yirgacheffe Light 250g", v1, rp1);
            FinishedProduct fp2 = new FinishedProduct(new BigDecimal("0.250"), new BigDecimal("650.00"), "Bourbon Medium 250g", v2, rp2);
            FinishedProduct fp3 = new FinishedProduct(new BigDecimal("0.500"), new BigDecimal("1190.00"), "Heirloom Dark 500g", v3, rp3);
            FinishedProduct fp4 = new FinishedProduct(new BigDecimal("1.000"), new BigDecimal("1490.00"), "Robusta Classic 1kg", v4, rp2);
            FinishedProduct fp5 = new FinishedProduct(new BigDecimal("0.250"), new BigDecimal("690.00"), "Caturra Vienna 250g", v5, rp4);
            FinishedProduct fp6 = new FinishedProduct(new BigDecimal("0.500"), new BigDecimal("1090.00"), "Bourbon Dark 500g", v2, rp3);
            List<FinishedProduct> products = List.of(fp1, fp2, fp3, fp4, fp5, fp6);
            products.forEach(em::persist);

            // ---- Вкусовые предпочтения клиентов (preferences, M:N) ----
            c1.addPreference(td1); c1.addPreference(td9);   // Иван: Шоколад, Плотное тело
            c2.addPreference(td2); c2.addPreference(td6);   // Анна: Цитрус, Высокая кислотность
            c4.addPreference(td9);                          // Мария: Плотное тело
            c5.addPreference(td4);                          // Алексей: Ягоды

            em.flush(); // получаем id всех сущностей выше перед созданием зависимых записей

            // ---- Подписки ----
            Subscription s1 = new Subscription(LocalDateTime.of(2026, 1, 10, 9, 0), "active", c1, r2);
            Subscription s2 = new Subscription(LocalDateTime.of(2026, 2, 1, 10, 30), "active", c2, r1);
            Subscription s3 = new Subscription(LocalDateTime.of(2026, 3, 15, 11, 0), "active", c3, r4);
            Subscription s4 = new Subscription(LocalDateTime.of(2026, 1, 20, 14, 0), "expired", c4, r3);
            Subscription s5 = new Subscription(LocalDateTime.of(2026, 4, 5, 8, 45), "active", c5, r5);
            Subscription s6 = new Subscription(LocalDateTime.of(2026, 2, 18, 16, 0), "active", c6, r2);
            Subscription s7 = new Subscription(LocalDateTime.of(2026, 5, 1, 12, 0), "not_active", c7, r1);
            Subscription s8 = new Subscription(LocalDateTime.of(2026, 3, 1, 9, 30), "active", c8, r4);
            List.of(s1, s2, s3, s4, s5, s6, s7, s8).forEach(em::persist);

            em.flush();

            // ---- Посылки ----
            Package pk1 = new Package(LocalDateTime.of(2026, 1, 24, 10, 0), "Bourbon Medium 250g x2", "TRK-1001", "delivered", s1);
            Package pk2 = new Package(LocalDateTime.of(2026, 2, 7, 10, 0), "Bourbon Medium 250g x2", "TRK-1002", "delivered", s1);
            Package pk3 = new Package(LocalDateTime.of(2026, 2, 15, 10, 0), "Yirgacheffe Light 250g x1", "TRK-1003", "delivered", s2);
            Package pk4 = new Package(LocalDateTime.of(2026, 4, 1, 10, 0), "Robusta Classic 1kg x1", "TRK-1004", "delivered", s3);
            Package pk5 = new Package(LocalDateTime.of(2026, 5, 1, 10, 0), "Robusta Classic 1kg x1", "TRK-1005", "shipped", s3);
            Package pk6 = new Package(LocalDateTime.of(2026, 1, 25, 10, 0), "Heirloom Dark 500g x1", "TRK-1006", "delivered", s4);
            Package pk7 = new Package(LocalDateTime.of(2026, 4, 12, 10, 0), "Bourbon Dark 500g x1", "TRK-1007", "delivered", s5);
            Package pk8 = new Package(LocalDateTime.of(2026, 4, 19, 10, 0), "Bourbon Dark 500g x1", "TRK-1008", "shipped", s5);
            Package pk9 = new Package(LocalDateTime.of(2026, 3, 1, 10, 0), "Bourbon Medium 250g x2", "TRK-1009", "delivered", s6);
            Package pk10 = new Package(LocalDateTime.of(2026, 3, 16, 10, 0), "Robusta Classic 1kg x1", "TRK-1010", "delivered", s8);
            Package pk11 = new Package(LocalDateTime.of(2026, 6, 1, 10, 0), "Caturra Vienna 250g x1", "TRK-1011", "pending", s6);
            List<Package> packages = List.of(pk1, pk2, pk3, pk4, pk5, pk6, pk7, pk8, pk9, pk10, pk11);
            packages.forEach(em::persist);

            em.flush();

            // ---- Состав посылок (package_composition: id_package + id_finished_product + quantity) ----
            em.persist(new PackageComposition(pk1, fp2, 2));
            em.persist(new PackageComposition(pk2, fp2, 2));
            em.persist(new PackageComposition(pk3, fp1, 1));
            em.persist(new PackageComposition(pk4, fp4, 1));
            em.persist(new PackageComposition(pk5, fp4, 1));
            em.persist(new PackageComposition(pk6, fp3, 1));
            em.persist(new PackageComposition(pk7, fp6, 1));
            em.persist(new PackageComposition(pk8, fp6, 1));
            em.persist(new PackageComposition(pk9, fp2, 2));
            em.persist(new PackageComposition(pk10, fp4, 1));
            em.persist(new PackageComposition(pk11, fp5, 1));

            // ---- Отзывы ----
            Review rv1 = new Review("Отличный кофе, ароматный!", new BigDecimal("4.5"), pk1, c1);
            Review rv2 = new Review("Хорошо, но хотелось бы потемнее обжарку", new BigDecimal("4.0"), pk2, c1);
            Review rv3 = new Review("Очень понравился вкус цитруса", new BigDecimal("5.0"), pk3, c2);
            Review rv4 = new Review("Стандартный робуста, ничего особенного", new BigDecimal("3.5"), pk4, c3);
            Review rv5 = new Review("Отличное тело у напитка, плотный вкус", new BigDecimal("4.5"), pk6, c4);
            Review rv6 = new Review("Лучший кофе, который пробовала!", new BigDecimal("5.0"), pk7, c5);
            Review rv7 = new Review("Доставка задержалась, но кофе хороший", new BigDecimal("4.0"), pk9, c6);
            Review rv8 = new Review("Очень насыщенный вкус", new BigDecimal("4.5"), pk10, c8);
            List<Review> reviews = List.of(rv1, rv2, rv3, rv4, rv5, rv6, rv7, rv8);
            reviews.forEach(em::persist);

            em.flush();

            // ---- Детализация отзывов по продуктам (review_item) ----
            em.persist(new ReviewItem(rv1, fp2, new BigDecimal("4.5"), "Прекрасный баланс сладости и кислотности"));
            em.persist(new ReviewItem(rv2, fp2, new BigDecimal("4.0"), "Можно обжарить чуть темнее"));
            em.persist(new ReviewItem(rv3, fp1, new BigDecimal("5.0"), "Яркий цитрусовый вкус"));
            em.persist(new ReviewItem(rv4, fp4, new BigDecimal("3.5"), "Простой, но добротный"));
            em.persist(new ReviewItem(rv5, fp3, new BigDecimal("4.5"), "Плотное тело, насыщенный шоколад"));
            em.persist(new ReviewItem(rv6, fp6, new BigDecimal("5.0"), "Идеальная тёмная обжарка"));
            em.persist(new ReviewItem(rv7, fp2, new BigDecimal("4.0"), "Стабильное качество"));
            em.persist(new ReviewItem(rv8, fp4, new BigDecimal("4.5"), "Хорошая крепость"));

            tx.commit();
            log.info("Начальные данные для Hibernate demo добавлены");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
