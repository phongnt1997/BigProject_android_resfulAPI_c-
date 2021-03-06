﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.repository
{
    public interface IRepository<T> where T : class
    {        
        IEnumerable<T> Gets();
        T GetByID(int id);
        void Insert(T model);
        void Delete(int id);
        void Edit(T model);
        void Save();
    }
}
