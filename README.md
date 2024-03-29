# java-kanban
Repository for homework project.
## Описание проекта
Проект java-kanban представляет собой бэкенд трекера задач.

В системе задачи могут быть трёх типов: обычные задачи, эпики и подзадачи. Для них должны выполняться следующие условия:
*Для каждой подзадачи известно, в рамках какого эпика она выполняется.
*Каждый эпик знает, какие подзадачи в него входят.
*Завершение всех подзадач эпика считается завершением эпика.

В нём реализованы следующие функции:
1. Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
2. Методы для каждого из типа задач(Задача/Эпик/Подзадача):
	2.1 Получение списка всех задач.
	2.2 Удаление всех задач.
	2.3 Получение по идентификатору.
	2.4Создание. Сам объект должен передаваться в качестве параметра.
	2.5 Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
	2.6 Удаление по идентификатору.
3. Дополнительные методы:
	Получение списка всех подзадач определённого эпика.
4. Управление статусами осуществляется по следующему правилу:
4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
4.2 Для эпиков:  
если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
во всех остальных случаях статус должен быть IN_PROGRESS.

## Технологичекий стек

Java 11

Проект является практической работой по курсу Яндекс.Практикум.Java-разработчик.

Проект подготовлен Русовым Андреем (rusov2106@mail.ru)
