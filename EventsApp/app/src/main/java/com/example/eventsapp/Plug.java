package com.example.eventsapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plug {

    private List<EventServerData> rawDataList = new ArrayList<>();
    private List<EventServerData> confirmedDataList = new ArrayList<>();


    EventServerData eventData1 = new EventServerData(111, "Конференция биологов", "Изучение идей, гипотез и теорий о целостности, системности природы, ее эволюции, в которых живые системы характеризуются как целостные, способные к саморегуляции и саморазвитию", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://www.iguides.ru/upload/medialibrary/053/05304c6e57e931af1c2519ae9ddd039b.png", "Москва");
    EventServerData eventData2 = new EventServerData(112, "Конференция садоводов", "Передача опыта по опылению яблонь", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://s1.stc.all.kpcdn.net/putevoditel/projectid_379258/images/tild3066-3638-4832-b035-373264613934__960.jpg", "Волгоград");
    EventServerData eventData3 = new EventServerData(113, "Конференция ювелиров", "Обсуждение вопроса огранки алмазов", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://static.wikia.nocookie.net/science/images/b/b0/Ags1.jpg/revision/latest/scale-to-width-down/340?cb=20090702050036&path-prefix=ru", "Санкт-Петербург");
    EventServerData eventData4 = new EventServerData(114, "Родительское собрание", "Сбор средств на новые занавески в класс руского языка и литературы", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://lh3.googleusercontent.com/proxy/KI7Q9khQMGJXylLIRvgRMuylkeZIoSA_weoFoq6FtmVaxS-UcgIvn3FK2eglmgAccZQi4Llx5UODT4k5460_cmKb3U2SZC0R0yEt1zur5EJOWko5znUbe1nDNg", "Москва");
    EventServerData eventData5 = new EventServerData(115, "Уфимский марафон", "Примите участие в ежегодном марафоне", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://letsportpeople.com/wp-content/uploads/2018/05/cologne-marathon-2018-featued-945x474.jpg", "Уфа");
    EventServerData eventData6 = new EventServerData(116, "Конференция астрологов", "Критическое сближение луны и плутона", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://astro21.ru/wp-content/uploads/2016/06/history2-1.jpg", "Челябинск");
    EventServerData eventData7 = new EventServerData(117, "Meetup разработчиков", "Использование jenkins в CI/CD", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://iamt.spbstu.ru/userfiles/images/icons/multi.jpg", "Урюпинск");
    EventServerData eventData8 = new EventServerData(118, "Экономический форум", "Обсуждение вопроса резкого роста биткоина", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://www.gov.kz/uploads/2019/5/20/9859c84c9ad9fc6ac9491b7759c2a531_original.150522.jpg", "Нью-Йорк");
    EventServerData eventData9 = new EventServerData(119, "Конференция агрономов", "Критический рост популяции медведок на территории Северной Осетии - Алании", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://intalent.pro/sites/default/files/styles/new_photo_in_article/public/foto/article/3_6.jpg?itok=VaqST9TE", "Владикавказ");
    EventServerData eventData10 = new EventServerData(1110, "Конференция физиков", "Обсуждени вопроса интерференции света", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://soyuzmash.ru/upload/iblock/ac4/ac49bf3af819c461500677ab18d7a934.jpeg", "Оклахома-Сити");
    EventServerData eventData11 = new EventServerData(1111, "Встреча с авторами", "Встреча с авторами книг", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://lh4.googleusercontent.com/JF5ll5h92znpF3dMZZM7oImhi9j_OH1LCT9on-zozUX0jXsU3wICu0LpvAbHCn0sro1szi2RXim5DPuqDUie7d1sJ0tJRVHefO97NwA4C_Ha06HBScmZtE3GuW2Im8MJvSF_zJdh", "Михайловка");
    EventServerData eventData12 = new EventServerData(1112, "Новогодний копоратив", "Встреча с сотрудниками по поводу окончания календарного года", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://laroflowers.ru/files/products/christmass_tree_002_2.600x600.jpg?aaa7e2800742ae80b4c6915a0f164acc", "Владимир");
    EventServerData eventData13 = new EventServerData(1113, "День рождения", "День рождения Петра Петрова", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://bonnycards.ru/images/birthday-woman/drwoman0213.jpg", "Токио");
    EventServerData eventData14 = new EventServerData(1114, "Экологическая конференция", "Обсуждение выбросов магнитогорского комбината", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://www.susu.ru/sites/default/files/styles/wide_news_image/public/field/image/1_271.jpg?itok=Hjqe3X-p", "Магнитогорск");
    EventServerData eventData15 = new EventServerData(1115, "Встреча педагогов", "Омен опытом по воспитанию детей", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://www.mgpu.ru/wp-content/uploads/2017/11/5-Online-Degrees-to-Help-you-with-your-Teaching-Career.jpg", "Анапа");
    EventServerData eventData16 = new EventServerData(1116, "Конференция по иностранным языкам", "Политкорректность в условиях современного мира", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://cs8.pikabu.ru/post_img/big/2016/07/15/6/1468575287199836539.jpg", "Стокгольм");
    EventServerData eventData17 = new EventServerData(1117, "Встреча выпускников", "Встреча выпускников 1977 года выпуска", "2020-05-01 00:00:00", "2020-05-01 00:00:00", "https://academygps.ru/upload/iblock/88d/88d94f1b3c1041bb6d211996cc2cd094.jpg", "Сыктывкар");


    public Plug (){
        rawDataList.add(eventData1);
        rawDataList.add(eventData2);
        rawDataList.add(eventData3);
        rawDataList.add(eventData4);
        rawDataList.add(eventData5);
        rawDataList.add(eventData6);
        rawDataList.add(eventData7);
        rawDataList.add(eventData8);
        rawDataList.add(eventData9);
        rawDataList.add(eventData10);
        rawDataList.add(eventData11);
        rawDataList.add(eventData12);
        rawDataList.add(eventData13);
        rawDataList.add(eventData14);
        rawDataList.add(eventData15);
        rawDataList.add(eventData16);
        rawDataList.add(eventData17);
    }


    public void addData(EventServerData eventServerData){
        rawDataList.add(eventServerData);
    }

    public List<EventServerData> getRawData (){
        return rawDataList;
    }

    public List<EventServerData> getConfirmedData (){
        if (rawDataList.size()> 2){
            double index = Math.random() * rawDataList.size();
            EventServerData tmp = rawDataList.get((int)index);
            rawDataList.remove((int)index);
            confirmedDataList.add(tmp);
        }
        return confirmedDataList;
    }

    public int rawDataSize(){
        return rawDataList.size();
    }
    public int confirmedDataSize(){
     return confirmedDataList.size();
    }
    public boolean deleteRawData(int id){
        int index = 0;
        boolean exist = false;
        for (int i = 0; i< rawDataList.size(); i++) {
            if (rawDataList.get(i).getId() == id) {
                index = i;
                exist = true;
            }
        }
        if (exist){
        rawDataList.remove(index);}

        return exist;
    }


}
