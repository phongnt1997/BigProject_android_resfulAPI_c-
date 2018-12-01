using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.taskscheduler.Ultility
{
    public static class DateUtil
    {
        public static List<DateTime> GetDates(int year, int month, int dayOfWeek)
        {
            var dates = Enumerable.Range(1, DateTime.DaysInMonth(year, month))
                 .Where(d => (int)new DateTime(year, month, d).DayOfWeek == dayOfWeek)
                .Select(d => new DateTime(year, month, d)).ToList();
            return dates;
        }
    }
}
