﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;
using cattocdi.entity;

namespace cattocdi.repository
{
    public class Repository<T> : IRepository<T> where T : class
    {
        private readonly CatTocDiContext context;
        private readonly IDbSet<T> dbSet;

        public Repository(CatTocDiContext _context)
        {
            context = _context;
            dbSet = context.Set<T>();
        }

        public void Delete(int id)
        {
            T model = dbSet.Find(id);
            dbSet.Remove(model);
        }

        public T GetByID(int id)
        {
            return dbSet.Find(id);
        }

        public IEnumerable<T> Gets()
        {
            return dbSet.ToList();
        }

        public void Insert(T model)
        {
            dbSet.Add(model);
        }

        public void Save()
        {
            context.SaveChanges();
        }

        public void Edit(T model)
        {
            context.Entry<T>(model).State = EntityState.Modified;
        }
    }
}
