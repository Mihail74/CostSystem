package ru.mkardaev.entrypoint;

import static ru.mkardaev.utils.Messages.Keys.CATEGORY_ADDED;
import static ru.mkardaev.utils.Messages.Keys.CATEGORY_FINDED;
import static ru.mkardaev.utils.Messages.Keys.CATEGORY_NOT_FINDED;
import static ru.mkardaev.utils.Messages.Keys.CHOOSE_OPERATION;
import static ru.mkardaev.utils.Messages.Keys.ERROR_READ_FILE;
import static ru.mkardaev.utils.Messages.Keys.ERROR_WRITE_FILE;
import static ru.mkardaev.utils.Messages.Keys.INPUT_CATEGORY_ID;
import static ru.mkardaev.utils.Messages.Keys.INPUT_CATEGORY_TITLE;
import static ru.mkardaev.utils.Messages.Keys.INPUT_NUMBER_IN_RANGE;
import static ru.mkardaev.utils.Messages.Keys.NOT_ALLOW_TO_READ;
import static ru.mkardaev.utils.Messages.Keys.OPERATION_ADD;
import static ru.mkardaev.utils.Messages.Keys.OPERATION_EXIT;
import static ru.mkardaev.utils.Messages.Keys.OPERATION_FIND;
import static ru.mkardaev.utils.Messages.Keys.WELCOME;
import static ru.mkardaev.utils.Messages.Keys.WRONG_FORMAT_ID;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Scanner;

import ru.mkardaev.factories.CategoryFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.utils.IdGenerator;
import ru.mkardaev.utils.Messages;

public class ConsoleApplication
{
    private static final int ID_OPERATION_ADD = 1;
    private static final int ID_OPERATION_EXIT = 3;
    private static final int ID_OPERATION_FIND = 2;
    private static final int OPERATIONS_AMOUNT = 3;
    private static final String PATH_DATA_FILE = "categories.txt";
    private static final String WRITE_FORMAT = "%d,%s,%s";
    private Messages messages = Messages.INSTANCE;

    public void run()
    {
        initializeIdGenerator();

        try (Scanner scanner = new Scanner(System.in))
        {
            printMessage(messages.getMessage(WELCOME));

            Long operationId = 0L;
            do
            {
                operationId = requestOperation(scanner);
                doOperation(operationId, scanner);
            }
            while (operationId != ID_OPERATION_EXIT);
        }

    }

    private void doOperation(Long operationId, Scanner scanner)
    {
        if (operationId == ID_OPERATION_EXIT)
        {
            return;
        }
        if (operationId == ID_OPERATION_FIND)
        {
            doOperationFind(scanner);
        }
        if (operationId == ID_OPERATION_ADD)
        {
            doOperationAdd(scanner);
        }
    }

    private void doOperationAdd(Scanner scanner)
    {
        String categoryTitle = requestCategoryTitle(scanner);
        Category category = CategoryFactory.createCategory(categoryTitle);

        if (writeCategoryToFile(Paths.get(PATH_DATA_FILE), category))
        {
            printMessage(messages.getMessage(CATEGORY_ADDED, category.getId(), category.getTitle()));
        }
        else
        {
            printMessage(messages.getMessage(ERROR_WRITE_FILE));
        }
    }

    private void doOperationFind(Scanner scanner)
    {
        Long requiredId = requestCategoryId(scanner);

        Category category = findRequiredCategory(requiredId);
        if (category == null)
        {
            printMessage(messages.getMessage(CATEGORY_NOT_FINDED));
        }
        else
        {
            printMessage(messages.getMessage(CATEGORY_FINDED, category.getId(), category.getTitle()));
        }

    }

    /**
     * Проивзоидт поиск категории в файле
     * 
     * @param file - файл в котором производится поиск
     * @param requiredId - id запрашиваемой категории
     * @return найденная категория или null
     */
    private Category findCategoryFromFile(Path file, Long requiredId)
    {
        try
        {
            if (Files.exists(file))
            {
                try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8))
                {
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        String[] elements = line.split(",");
                        Long id = Long.parseLong(elements[0]);
                        if (id == requiredId)
                        {
                            return CategoryFactory.createCategory(id, elements[1]);
                        }

                    }
                }
                catch (IOException e)
                {
                    printMessage(messages.getMessage(ERROR_READ_FILE));
                }
            }
        }
        catch (SecurityException e)
        {
            printMessage(messages.getMessage(NOT_ALLOW_TO_READ));
        }
        return null;
    }

    /**
     * Производит поиск категории по запрошенному id
     * 
     * @param requiredId - Звпрошенный Id категории
     * @return найденную категорию, либо null
     */
    private Category findRequiredCategory(Long requiredId)
    {
        Path file = Paths.get(PATH_DATA_FILE);
        return findCategoryFromFile(file, requiredId);
    }

    /**
     * Пробегает по всему файлу {@link #PATH_DATA_FILE} и находит максимальный id. Устанавливает id+1 в качестве начального значения для
     * {@link IdGenerator} . В случае ошибок, начальным значением {@link IdGenerator} устанавливается 1.
     */
    private void initializeIdGenerator()
    {
        try
        {
            Path file = Paths.get(PATH_DATA_FILE);
            if (Files.exists(file))
            {
                long maxId = 1L;
                try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8))
                {
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        String[] elements = line.split(",");
                        String id = elements[0];
                        maxId = Math.max(maxId, Long.parseLong(id));
                    }
                }
                catch (IOException e)
                {
                    printMessage(messages.getMessage(ERROR_READ_FILE));
                }
                IdGenerator.setNextId(maxId + 1L);
            }
        }
        catch (SecurityException e)
        {
            printMessage(messages.getMessage(NOT_ALLOW_TO_READ));
        }

    }

    private void printMessage(String message)
    {
        System.out.println(message);
    }

    /**
     * Запрашивает у пользователя id category
     * 
     * @param scanner
     * @return введенное пользователем id
     */
    private Long requestCategoryId(Scanner scanner)
    {
        printMessage(messages.getMessage(INPUT_CATEGORY_ID));
        return requestNumber(scanner);

    }

    /**
     * Запрашивает у пользователя название категории
     * 
     * @param scanner
     * @return введенное пользователем значение
     */
    private String requestCategoryTitle(Scanner scanner)
    {
        printMessage(messages.getMessage(INPUT_CATEGORY_TITLE));
        String title = scanner.nextLine();
        return title;
    }

    /**
     * Ожидает ввода числа
     * 
     * @return введенное число
     */
    private Long requestNumber(Scanner scanner)
    {
        Long inputId = null;
        boolean isCorrectInput = false;
        do
        {
            String input = scanner.nextLine();
            try
            {
                inputId = Long.parseLong(input);
                isCorrectInput = true;
            }
            catch (NumberFormatException e)
            {
                printMessage(messages.getMessage(WRONG_FORMAT_ID));
                isCorrectInput = false;
            }
        }
        while (!isCorrectInput);
        return inputId;
    }

    private Long requestOperation(Scanner scanner)
    {
        printMessage(messages.getMessage(CHOOSE_OPERATION));
        printMessage(messages.getMessage(OPERATION_ADD));
        printMessage(messages.getMessage(OPERATION_FIND));
        printMessage(messages.getMessage(OPERATION_EXIT));

        boolean isCorrectInput = false;
        Long operationId;
        do
        {
            operationId = requestNumber(scanner);
            isCorrectInput = operationId >= 1 && operationId <= OPERATIONS_AMOUNT;
            if (!isCorrectInput)
            {
                printMessage(messages.getMessage(INPUT_NUMBER_IN_RANGE, 1, OPERATIONS_AMOUNT));
            }
        }
        while (!isCorrectInput);
        return operationId;
    }

    /**
     * Записывает в файл file запись о категории category
     * 
     * @return true, если запись была произведена, false в случае ошибки записи
     */
    private boolean writeCategoryToFile(Path file, Category category)
    {
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
        {
            bw.write(String.format(WRITE_FORMAT, category.getId(), category.getTitle(), new Date()));
            bw.newLine();
            bw.flush();
        }
        catch (IOException e)
        {
            return false;
        }
        return true;
    }
}
