﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class WorkingHourViewModel
    {
        public int DayOfWeek { get; set; }
        public string FromHour { get; set; }
        public string ToHour { get; set; }
        public bool IsClosed { get; set; }
    }
}

