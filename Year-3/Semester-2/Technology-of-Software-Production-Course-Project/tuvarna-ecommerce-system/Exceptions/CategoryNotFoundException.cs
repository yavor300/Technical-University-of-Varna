namespace tuvarna_ecommerce_system.Exceptions
{
    public class CategoryNotFoundException : Exception
    {
        public int CategoryId { get; }

        public CategoryNotFoundException(int categoryId)
            : base($"Category with ID {categoryId} not found.")
        {
            CategoryId = categoryId;
        }

        public CategoryNotFoundException(string message)
            : base(message)
        {
        }

        public CategoryNotFoundException(string message, Exception innerException)
            : base(message, innerException)
        {
        }
    }
}