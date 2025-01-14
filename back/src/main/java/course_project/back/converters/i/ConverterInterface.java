package course_project.back.converters.i;

public interface ConverterInterface<DTO, Entity> {
    public Entity fromDTO(DTO DTO);

    public DTO fromEntity(Entity entity);

    public Entity getFromDB(DTO DTO);
}
