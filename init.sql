-- Создание таблицы messages, если она ещё не существует
CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL
);

-- Вставка тестового сообщения
INSERT INTO messages (content) VALUES ('Hello from PostgreSQL!');

-- Уведомление при запуске скрипта
DO $$
BEGIN
    RAISE NOTICE 'Table "messages" has been created and initialized!';
END;
$$;
