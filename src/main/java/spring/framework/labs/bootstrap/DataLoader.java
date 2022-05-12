//package spring.framework.labs.bootstrap;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import spring.framework.labs.domain.Author;
//import spring.framework.labs.domain.Book;
//import spring.framework.labs.domain.Publisher;
//import spring.framework.labs.domain.dtos.AuthorDTO;
//import spring.framework.labs.domain.dtos.BookDTO;
//import spring.framework.labs.domain.dtos.PublisherDTO;
//import spring.framework.labs.mappers.AuthorMapper;
//import spring.framework.labs.mappers.BookMapper;
//import spring.framework.labs.mappers.PublisherMapper;
//import spring.framework.labs.services.AuthorService;
//import spring.framework.labs.services.BookService;
//import spring.framework.labs.services.PublisherService;
//
//import java.util.List;
//import java.util.Set;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final AuthorService authorService;
//    private final PublisherService publisherService;
//    private final BookService bookService;
//
//    public DataLoader(AuthorService authorService, PublisherService publisherService, BookService bookService) {
//        this.authorService = authorService;
//        this.publisherService = publisherService;
//        this.bookService = bookService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        int size = bookService.findAll().size();
//
//        if (size == 0) {
//            loadData();
//        }
//    }
//
//    private void loadData() {
//        BookMapper bookMapper = BookMapper.INSTANCE;
//        AuthorMapper authorMapper = AuthorMapper.INSTANCE;
//        PublisherMapper publisherMapper = PublisherMapper.INSTANCE;
//
//        Author author = Author.builder().books(Set.of()).description("").image("").name("Author name!").build();
//
//        Publisher publisher = Publisher.builder().books(Set.of()).name("Publisher name!").build();
//
//        Book book = Book.builder().article("p6113365").rating(4.7)
//                .images(new String[]{"https://cdn.book24.ru/v2/ASE000000000863139/COVER/cover4__w820.webp", "https://cdn.book24.ru/v2/ASE000000000863139/COVER/cover13d__w820.webp"})
//                .author(author).category("Современная русская проза").publisher(publisher)
//                .isbn("978-5-17-148218-3").yearOfPublishing(2022).numberOfPages(320)
//                .format("130x200 мм").weight(0.36).price(564L).name("За стенкой. История Отиса Ревиаля")
//                .description("ДЛЯ АУДИТОРИИ БЕСТСЕЛЛЕРОВ «КРАСНЫЙ, БЕЛЫЙ И КОРОЛЕВСКИЙ СИНИЙ» И «ВСЕГО ЛИШЬ ПОЛНОСТЬЮ РАЗДАВЛЕН».\n" +
//                        "\n" +
//                        "СМЕШНАЯ И ТРОГАТЕЛЬНАЯ ИСТОРИЯ О ТОМ, КАК ТОНКАЯ СТЕНКА МОЖЕТ БЫТЬ НАМНОГО ТОЛЩЕ, ЧЕМ КАЖЕТСЯ НА ПЕРВЫЙ ВЗГЛЯД\n" +
//                        "\n" +
//                        "Первая книга в серии «Звезды молодежной прозы на русском», аналога серии «Звезды молодежной прозы», но с российскими авторами.\n" +
//                        "\n" +
//                        "История любви, которая понравится всем фанатам Софи Гонзалес и Кейси Маккуинстон.\n" +
//                        "\n" +
//                        "Острые темы, живые герои, яркие диалоги и юмор.\n" +
//                        "\n" +
//                        "Отис наивно полагал, что после некоторых неприятных инцидентов в старшей школе сможет держать свои эмоции под контролем и больше никогда не совершать необдуманных поступков. Но и на этот раз все его планы разлетелись в пух и прах - стоило лишь раз взглянуть в голубые глаза соседа, живущего за стенкой. И стенка эта настолько тонкая, что Отис становится невольным слушателем всей бурной жизни Конарда. И вот однажды, после нелепой первой встречи, Отис понимает, что уже не в силах выкинуть соседа из головы. Однако прошлое напоминает о себе в самый неподходящий момент, вставая на пути к их счастью.")
//                .build();
//
//        AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(author);
//        PublisherDTO publisherDTO = publisherMapper.publisherToPublisherDTO(publisher);
//        BookDTO bookDTO = bookMapper.bookToBookDTO(book);
//
//        bookService.save(bookDTO);
//        publisherService.save(publisherDTO);
//        authorService.save(authorDTO);
////        Book book1 = new Book();
////        book1.setId(1L);
////        book1.setPrice(785L);
////        book1.setName("От первого лица");
////        book1.setDescription("Впервые на русском. Новый сборник рассказов от мастера современной прозы, уникального, всемирно известного японского писателя Харуки Мураками.\n" +
////                "\n" +
////                "Восемь рассказов «От первого лица» в фирменном стиле автора.\n" +
////                "\n" +
////                "Микс из историй разных по содержанию, но с гарантированным качеством. Что из историй реальность, а что вымысел — каждый решает сам.\n" +
////                "\n" +
////                "Для тех, кто будет впервые знакомиться с творчеством Харуки Мураками, это прекрасная возможность погрузиться в его мир.\n" +
////                "\n" +
////                "Мир ежедневных забот и загадочных событий, мир, в котором много деталей и нюансов. Мир любви и потерь, забавных встреч и фатальных событий, бейсбола и музыки. Где можно поболтать за кружкой пива с говорящей обезьяной об абсолютной любви, получить по почте тетрадь со стихами, узнать об индикаторе любви под музыку «Битлз».\n" +
////                "\n" +
////                "Мир, в котором больше смысла и глубины, чем кажется на первый взгляд. Поэтому книги Харуки Мураками перечитывают не один раз.\n" +
////                "\n" +
////                "Для тех, кто хорошо знаком с творчеством Харуки Мураками, это огромная радость — новая книга любимого автора. Ее нельзя пропустить.");
////        book1.setAuthor("Харуки Мураками");
////        book1.setPublisher("Эксмо");
////
////        Book book2 = new Book();
////        book2.setId(2L);
////        book2.setPrice(864L);
////        book2.setName("Билли Саммерс");
////        book2.setDescription("Новый увлекательный роман от автора культовых бестселлеров «Оно», «Чужак» и «Мистер Мерседес» Стивена Кинга!\n" +
////                "\n" +
////                "Смесь нуарного боевика с крутым детективом, современным вестерном, дорожным романом и военными мемуарами.\n" +
////                "\n" +
////                "Лучшая книга Стивена Кинга за долгое время по версии газеты The Guardian.\n" +
////                "\n" +
////                "Билли Саммерс — профессиональный киллер с жестким моральным кодексом: он принимает заказы только на действительно «плохих парней». Но ему, бывшему морпеху, это занятие не по душе, и однажды он решает отойти от дел, чтобы начать новую жизнь. Перед этим Билли предстоит выполнить еще один заказ, который обеспечит ему безбедное существование.\n" +
////                "\n" +
////                "Его чутье и опыт подсказывают: в этом деле что-то не так и оно не такое простое, как кажется на первый взгляд.\n" +
////                "\n" +
////                "Однако на кону стоят слишком большие деньги.\n" +
////                "\n" +
////                "И Билли отправляется в тихий провинциальный городок Ред-Блафф и начинает тщательную подготовку к своему последнему выстрелу.\n" +
////                "\n" +
////                "Последнему ли?..");
////        book2.setAuthor("Стивен Кинг");
////        book2.setPublisher("АСТ");
////
////        Book book3 = new Book();
////        book3.setId(3L);
////        book3.setPrice(637L);
////        book3.setName("Code. Носители");
////        book3.setDescription("ПРОДОЛЖЕНИЕ БЕСТСЕЛЛЕРА «THE ONE. ЕДИНСТВЕННЫЙ»\n" +
////                "\n" +
////                "Триллер в духе «Черного зеркала» от лауреата премии International Thriller Writers Award 2021\n" +
////                "\n" +
////                "Лучший фантастический триллер-2021 по версии Wall Street Journal\n" +
////                "\n" +
////                "В XXI веке информация – бог. Но как ее защитить? Любое хранилище можно взломать, кроме…\n" +
////                "\n" +
////                "Несколько обычных людей выбраны правительством для участия в уникальной программе защиты данных. После новейших биоинженерных операций они становятся ходячими тайниками, Носителями. Отныне их мозг содержит сверхсекретные сведения, зашифрованные в генетическом коде. Взамен они получили шанс решить все свои проблемы и начать жизнь с чистого листа.\n" +
////                "\n" +
////                "Вместе Носители знают все грязные тайны, всю правду, скрывающуюся за каждой государственной ложью, за каждой теорией заговора. Но можно ли им доверять? Ведь у них тоже есть секреты, и они сделают все, чтобы их защитить…\n" +
////                "\n" +
////                "Роман прямо напрашивается: «NETFLIX, экранизируй меня!» – My Chestnut Reading Tree\n" +
////                "\n" +
////                "С это книгой я, словно кролик, выхваченный из тьмы фарами, зачарованно смотрел на источник света – и был безжалостно сбит тем, что оказалось за ним. – Books from Dusk till Dawn\n" +
////                "\n" +
////                "Завораживающе и в то же время невероятно пугающе. – Totally Booked\n" +
////                "\n" +
////                "Увлекательный и крайне правдоподобный триллер Маррса поднимает интересные вопросы о нашем будущем, где наука станет играть первую скрипку. – Booklist\n" +
////                "\n" +
////                "Чрезвычайно мощная комбинация умного сюжета, проблем технофобии, конспирологического триллера и шокирующих личных историй. – SFX Magazine\n" +
////                "\n" +
////                "Маррс поистине блестящ в лихих поворотах сюжета и адреналиновой гонке повествования. – Питер Джеймс\n" +
////                "\n" +
////                "Шок на каждой следующей странице. – Wall Street Journal");
////        book3.setAuthor("Джон Маррс");
////        book3.setPublisher("Эксмо");
////
////        Book book4 = new Book();
////        book4.setId(4L);
////        book4.setPrice(867L);
////        book4.setName("Чистый код");
////        book4.setDescription("Бестселлер от эксперта в области разработки ПО Роберта Мартина по рефакторингу: искусству исправления и очистки программного кода.");
////        book4.setAuthor("Роберт Мартин");
////        book4.setPublisher("Питер");
////
////        bookService.save(book1);
////        bookService.save(book2);
////        bookService.save(book3);
////        bookService.save(book4);
//    }
//}
