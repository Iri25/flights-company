package repository.file;

import domain.Entity;
import domain.validators.Validator;
import repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;

    /**
     * constructor with parameters @param fileName, @param validator
     * @param fileName
     * @param validator
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        //loadData();

    }

    /**
     * upload data to file
     */
    protected void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = bufferedReader.readLine())!=null){
                List<String> attribute = Arrays.asList(line.split(";"));
                E e=extractEntity(attribute);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  create an entity of type E having a specified list of @param attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes) throws IllegalArgumentException;

    /**
     * create @param entity of type E as string
     * @param entity
     * @return an entity of type E
     */
    protected abstract String createEntityAsString(E entity);

    /**
     * save
     * @param entity
     *         entity must be not null
     * @return the entity of type E saved
     */
    @Override
    public E save(E entity){
        E e = super.save(entity);
        if (e == null)
        {
            writeToFile(entity);
        }
        return e;
    }

    /**
     * delete
     * @param id
     *      id must be not null
     * @return the entity of type E deleted
     */
    @Override
    public E delete(ID id){
        E e=super.delete(id);
        if (e != null) {
            clearFile();
            for (E entity : super.findAll()) {
                writeToFile(entity);
            }
        }
        return e;
    }

    /**
     * update
     * @param entity
     *      entity must be not null
     * @return the entity of type E updated
     */
    @Override
    public E update(E entity) {

        E e = super.update(entity);
        if (e == null) {
            clearFile();
            for (E fr : super.findAll()) {
                writeToFile(fr);
            }
        }
        return e;
    }

    /**
     * write @param entity to the file
     * @param entity
     */
    protected void writeToFile(E entity){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName,true))) {
            bufferedWriter.write(createEntityAsString(entity));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * clear file
     */
    protected void clearFile(){
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
