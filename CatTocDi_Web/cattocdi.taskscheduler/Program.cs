using cattocdi.entity;
using cattocdi.repository;
using cattocdi.taskscheduler.Services.Implement;
using cattocdi.taskscheduler.Services.Interface;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;
using Unity.Lifetime;

namespace cattocdi.taskscheduler
{
    class Program
   {
        static void Main(string[] args)
        {
            // CONFIG UNITY IOC
            var container = new UnityContainer();
            container.RegisterType<CatTocDiContext>(new ContainerControlledLifetimeManager());
            container.RegisterType<IUnitOfWork, UnitOfWork>();
            container.RegisterType(typeof(IRepository<>), typeof(Repository<>));
            container.RegisterType<ITimeSlotService, TimeSlotService>();

            var timeSlotServices = container.Resolve<ITimeSlotService>();
            timeSlotServices.ScheduleTimeSlot();
            

            Console.ReadLine();

        }
    }
}
