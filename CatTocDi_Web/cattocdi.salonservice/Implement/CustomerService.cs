using cattocdi.entity;
using cattocdi.repository;
using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Implement
{
    //LOve DAT
    public class CustomerService : ICustomerService
    {
        private IRepository<Customer> _customerRepo;
        private IRepository<Appointment> _apmRepo;
        private IRepository<Salon> _salonRepo;
        private IUnitOfWork _unitOfWork;
        public CustomerService(IRepository<Customer> customerRepo, IUnitOfWork unitOfWork, IRepository<Appointment> apmRepo, IRepository<Salon> salonRepo)
        {
            _customerRepo = customerRepo;
            _unitOfWork = unitOfWork;
            _apmRepo = apmRepo;
            _salonRepo = salonRepo;

        }
        public void CreateCustomerAccount(CustomerViewModel model)
        {
            if (model != null)
            {
                Customer newCustomer = new Customer
                {
                    FirstName = model.Firstname,
                    LastName = model.Lastname,
                    Gender = model.Gender,
                    AccountId = model.AccountId,
                    Phone = model.Phone
                                        
                };
                _customerRepo.Insert(newCustomer);
                _unitOfWork.SaveChanges(); 
            }
        }

        public List<CustomerViewModel> GetAllCustomer(string AccountId)
        {
            List<int> salonServices = _salonRepo.Gets().Where(p => p.AccountId.Equals(AccountId))
                .Select(x => x.SalonServices.Where(v => v.SalonId == x.Id)
                .Select(l => l.Id)).FirstOrDefault().ToList();
            var customers = _apmRepo.Gets().Where(p => p.ServiceAppointments.Where(x => salonServices.Contains(x.ServiceId)).Count() > 0).Select(m => m.Customer).Select(q => new CustomerViewModel {
                    CustomerId = q.CustomerId,
                    Firstname = q.FirstName,
                    Lastname = q.LastName,
                    Gender = q.Gender ?? false,
                    Phone = q.Phone
            }).ToList();
            return customers;
        }

        public CustomerDetailViewModel GetById(int id)
        {
            var cus = _customerRepo.GetByID(id);
            var result = new CustomerDetailViewModel {
                CustomerId = cus.CustomerId,
                Email = cus.Email,
                Firstname = cus.FirstName,
                Lastname = cus.LastName,
                Gender = cus.Gender ?? false,
                Phone = cus.Phone,
                Appointments = cus.Appointments.Select(x => new AppointmentViewmodel
                {
                    AppointmentId = x.Id,
                    BookedDate = x.BookedDate,
                    CustomerId = x.CustomerId,
                    Duration =x.Duration,
                    Customer = new CustomerViewModel
                    {
                        CustomerId = x.Customer.CustomerId,
                        Firstname = x.Customer.FirstName,
                        Lastname = x.Customer.LastName,
                        Gender = x.Customer.Gender ?? false,
                        Phone = x.Customer.Phone

                    },
                    Promotion = x.Promotion != null ? new PromotionViewModel
                    {
                        Description = x.Promotion.Description,
                        DiscountPercent = x.Promotion.DiscountPercent,
                        EndTime = x.Promotion.EndTime,
                        StartTime = x.Promotion.StartTime,
                        Id = x.PromotionId ?? 0,
                        Status = x.Promotion.Status ?? 0
                    } : null,
                    Services = x.ServiceAppointments.Select(p => p.SalonService).Select(q => new SalonServiceViewModel
                    {
                        AvarageTime = q.AvarageTime ?? 0,
                        Price = q.Price ?? 0,
                        ServiceId =q.ServiceId,
                        ServiceName =q.Service.Name
                        
                    }).ToList(),
                    Status = x.Status,
                    //TimeSlot = x.TimeSlot
                }).ToList()
            };
            return result;
           
        }
    }
}
