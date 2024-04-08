namespace tuvarna_ecommerce_system.Exceptions
{
    public class EntityNotFoundException : Exception
    {
        public int EntityId { get; }
        public string EntityName { get; }

        public EntityNotFoundException(int id, string entityName)
            : base($"{entityName} with ID {id} not found.")
        {
            EntityId = id;
            EntityName = entityName;
        }

        public EntityNotFoundException(string message)
            : base(message)
        {
        }

        public EntityNotFoundException(string message, Exception innerException)
            : base(message, innerException)
        {
        }
    }
}