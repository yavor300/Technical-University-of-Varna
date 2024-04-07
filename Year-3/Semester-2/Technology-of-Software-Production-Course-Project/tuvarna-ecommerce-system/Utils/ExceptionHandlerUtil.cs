using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Exceptions;

namespace tuvarna_ecommerce_system.Utils
{
    public static class ExceptionHandlerUtil
    {
        public static void HandleDbUpdateException<T>(
            ILogger<T> logger,
            DbUpdateException ex,
            string entityName,
            string duplicateKeyMessage,
            string generalErrorMessage)
        {
            if (ex.InnerException is SqlException sqlEx && (sqlEx.Number == 2601 || sqlEx.Number == 2627))
            {
                logger.LogError(ex, duplicateKeyMessage, entityName);
                throw new CustomDuplicateKeyException($"{entityName}: Entity is already existing.", ex);
            }

            logger.LogError(ex, generalErrorMessage, entityName);
        }
    }
}
