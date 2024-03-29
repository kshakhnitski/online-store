# Online Store

Приложение онлайн магазина

## Description

**Сущности**:

- `User`  - пользователь приложения
  - `id` - идентификатор пользователя
  - `email` - имя пользователя
  - `password` - пароль пользователя
  - `firstName` - имя пользователя
  - `phone` - телефон пользователя
- `Product` - товар в каталоге
  - `id` - идентификатор товара
  - `name` - название товара
  - `description` - описание товара
  - `price` - цена товара
  - `category` - категория товара
  - `quantity` - количество товара
  - `status` - статус товара (в продаже, не в продаже
- `Category` - категория товара
  - `id` - идентификатор категории
  - `name` - название категории
- `Order` - заказ пользователя
  - `id` - идентификатор заказа
  - `user` - пользователь, оформивший заказ
  - `items` - товары в заказе
  - `status` - статус заказа
- `OrderItem` - товар в заказе
  - `id` - идентификатор товара в заказе
  - `product` - товар
  - `quantity` - количество товара
  - `price` - цена товара

Для обычного пользователя доступны следующие возможности:

- Просмотр каталога товаров
- Просмотр информации о товаре
- Добавление товара в корзину
- Просмотр корзины
- Оформление заказа
- Просмотр истории заказов
- Просмотр профиля
- Редактирование профиля

Для администратора доступны следующие возможности:

- `Товары`:
  - Просмотр каталога товаров
  - Просмотр информации о товаре
  - Добавление товара в каталог
  - Редактирование информации о товаре
  - Удаление товара из каталога

- `Заказы`:
  - Просмотр списка заказов
  - Просмотр информации о заказе
  - Редактирование информации о заказе
  - Удаление заказа

- `Пользователи`:
  - Просмотр списка пользователей
  - Просмотр информации о пользователе
  - Редактирование информации о пользователе
  - Удаление пользователя

- `Категории`:
  - Просмотр списка категорий
  - Просмотр информации о категории
  - Добавление категории
  - Редактирование информации о категории
  - Удаление категории

Микросервисы:

- `Catalog` - каталог товаров
- `Order` - заказы
- `User` - пользователи
- `Auth` - аутентификация
- `Gateway` - шлюз
