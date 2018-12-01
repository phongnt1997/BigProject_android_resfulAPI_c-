using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.repository
{
    public interface IUnitOfWork : IDisposable
    {
        int SaveChanges();
        DbRawSqlQuery<T> SQLQuery<T>(string sql, params object[] parameters);
        int ExecuteSqlCommand(string sql, params object[] parameters);
    }
}
