package com.tsel.app.util;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Data
@Slf4j
@AllArgsConstructor
public final class FileBufferUtil {

    private static final String EXTENSION = ".txt";
    private static final Gson GSON = new Gson();

    private String path;

    /**
     * Перезаписать список файлов
     * @param tClass Класс объектов
     * @param tList Лист объектов
     * @param <T> Класс объектов
     * @return True, если запись прошла успешно
     */
    public <T> boolean addObjectsToBuff(Class<T> tClass, List<T> tList) {
        return addObjectsToFile(tClass, tList, false);
    }

    public <T> boolean addObjectsToBuff(Class<T> tClass, Set<T> tSet) {
        return addObjectsToFile(tClass, new ArrayList<>(tSet), false);
    }

    /**
     * Записать список классов в конец файла
     * @param tClass Класс объектов
     * @param tList Лист объектов
     * @param <T> Класс объектов
     * @return True, если запись прошла успешно
     */
    public <T> boolean addObjectsToBuffEnd(Class<T> tClass, List<T> tList) {
        return addObjectsToFile(tClass, tList, true);
    }

    public <T> boolean addObjectsToBuffEnd(Class<T> tClass, Set<T> tSet) {
        return addObjectsToFile(tClass, new ArrayList<>(tSet), true);
    }

    /**
     * Получить объекты из файла
     * @param tClass Класс объекта
     * @param <T> Класс объекта
     * @return Лист объектов
     */
    public <T> List<T> getObjectsFromBuff(Class<T> tClass) {
        if (isFileExist(tClass)) {
            try (FileReader reader = new FileReader(resolveFileName(tClass))) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                List<T> objList = new ArrayList<>();
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    objList.add(GSON.fromJson(json, tClass));
                }
                return objList;
            } catch (IOException e) {
                log.error(format("Can't get object \"%s\" from file \"%s\"", tClass.getName(),
                        resolveFileName(tClass)), e);
            }
        }
        return emptyList();
    }

    /**
     * Отчистить файловый буффер
     * @param tClass Класс объекта
     * @param <T> Класс объекта
     * @return True, если файл успешно удалён
     */
    public <T> boolean clearBuff(Class<T> tClass) {
        return new File(resolveFileName(tClass)).delete();
    }

    private <T> boolean isFileExist(Class<T> tClass) {
        File file = new File(resolveFileName(tClass));
        return file.exists() && file.isFile() && file.length() > 0;
    }

    private <T> boolean addObjectsToFile(Class<T> tClass, List<T> tList, boolean appender) {
        createFile(tClass);
        try (FileWriter writer = new FileWriter(resolveFileName(tClass), appender)) {
            List<String> jsonList = tList.stream()
                .map(GSON::toJson)
                .collect(Collectors.toList());
            for (String json: jsonList) {
                writer.write(json + "\n");
            }
        } catch (IOException e) {
            log.error(format("Can't add object \"%s\" to file \"%s\"", tClass.getName(), resolveFileName(tClass)), e);
            return false;
        }
        return true;
    }

    private <T> void createFile(Class<T> tClass) {
        try {
            File file = new File(resolveFileName(tClass));
            File dir = new File(path);
            dir.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            log.error(format("Can't create file \"%s\"", resolveFileName(tClass)), e);
        }
    }

    private <T> String resolveFileName(Class<T> tClass) {
        return path + tClass.getSimpleName() + EXTENSION;
    }
}
