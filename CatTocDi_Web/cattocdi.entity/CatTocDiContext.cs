namespace cattocdi.entity
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class CatTocDiContext : DbContext
    {
        public CatTocDiContext()
            : base("name=CatTocDi")
        {
        }

        public virtual DbSet<C__MigrationHistory> C__MigrationHistory { get; set; }
        public virtual DbSet<Appointment> Appointments { get; set; }
        public virtual DbSet<AspNetRole> AspNetRoles { get; set; }
        public virtual DbSet<AspNetUserClaim> AspNetUserClaims { get; set; }
        public virtual DbSet<AspNetUserLogin> AspNetUserLogins { get; set; }
        public virtual DbSet<AspNetUser> AspNetUsers { get; set; }
        public virtual DbSet<ClosedDay> ClosedDays { get; set; }
        public virtual DbSet<Customer> Customers { get; set; }
        public virtual DbSet<Image> Images { get; set; }
        public virtual DbSet<Promotion> Promotions { get; set; }
        public virtual DbSet<Review> Reviews { get; set; }
        public virtual DbSet<Salon> Salons { get; set; }
        public virtual DbSet<SalonService> SalonServices { get; set; }
        public virtual DbSet<Service> Services { get; set; }
        public virtual DbSet<ServiceAppointment> ServiceAppointments { get; set; }
        public virtual DbSet<ServiceCategory> ServiceCategories { get; set; }
        public virtual DbSet<SlotAppointment> SlotAppointments { get; set; }
        public virtual DbSet<SlotTime> SlotTimes { get; set; }
        public virtual DbSet<sysdiagram> sysdiagrams { get; set; }
        public virtual DbSet<TimeSlot> TimeSlots { get; set; }
        public virtual DbSet<WorkingHour> WorkingHours { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Appointment>()
                .HasMany(e => e.Reviews)
                .WithRequired(e => e.Appointment)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Appointment>()
                .HasMany(e => e.ServiceAppointments)
                .WithRequired(e => e.Appointment)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<AspNetRole>()
                .HasMany(e => e.AspNetUsers)
                .WithMany(e => e.AspNetRoles)
                .Map(m => m.ToTable("AspNetUserRoles").MapLeftKey("RoleId").MapRightKey("UserId"));

            modelBuilder.Entity<AspNetUser>()
                .HasMany(e => e.AspNetUserClaims)
                .WithRequired(e => e.AspNetUser)
                .HasForeignKey(e => e.UserId);

            modelBuilder.Entity<AspNetUser>()
                .HasMany(e => e.AspNetUserLogins)
                .WithRequired(e => e.AspNetUser)
                .HasForeignKey(e => e.UserId);

            modelBuilder.Entity<AspNetUser>()
                .HasMany(e => e.Customers)
                .WithRequired(e => e.AspNetUser)
                .HasForeignKey(e => e.AccountId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Phone)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Email)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .HasMany(e => e.Appointments)
                .WithRequired(e => e.Customer)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Image>()
                .Property(e => e.Url)
                .IsUnicode(false);

            modelBuilder.Entity<Salon>()
                .Property(e => e.Phone)
                .IsUnicode(false);

            modelBuilder.Entity<Salon>()
                .Property(e => e.Email)
                .IsUnicode(false);

            modelBuilder.Entity<Salon>()
                .HasMany(e => e.ClosedDays)
                .WithRequired(e => e.Salon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Salon>()
                .HasMany(e => e.Promotions)
                .WithRequired(e => e.Salon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Salon>()
                .HasMany(e => e.SalonServices)
                .WithRequired(e => e.Salon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Salon>()
                .HasMany(e => e.SlotTimes)
                .WithRequired(e => e.Salon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Salon>()
                .HasMany(e => e.WorkingHours)
                .WithRequired(e => e.Salon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<SalonService>()
                .HasMany(e => e.ServiceAppointments)
                .WithRequired(e => e.SalonService)
                .HasForeignKey(e => e.ServiceId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Service>()
                .HasMany(e => e.SalonServices)
                .WithRequired(e => e.Service)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<ServiceCategory>()
                .HasMany(e => e.Services)
                .WithRequired(e => e.ServiceCategory)
                .HasForeignKey(e => e.CategoryId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<SlotTime>()
                .HasMany(e => e.SlotAppointments)
                .WithOptional(e => e.SlotTime)
                .HasForeignKey(e => e.SlotId);
        }
    }
}
