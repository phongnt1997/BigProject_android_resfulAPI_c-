USE [master]
GO
/****** Object:  Database [CatTocDi]    Script Date: 9/24/2018 9:21:14 PM ******/
CREATE DATABASE [CatTocDi]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CatTocDi', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\CatTocDi.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CatTocDi_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\CatTocDi_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CatTocDi].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CatTocDi] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CatTocDi] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CatTocDi] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CatTocDi] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CatTocDi] SET ARITHABORT OFF 
GO
ALTER DATABASE [CatTocDi] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CatTocDi] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CatTocDi] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CatTocDi] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CatTocDi] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CatTocDi] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CatTocDi] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CatTocDi] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CatTocDi] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CatTocDi] SET  DISABLE_BROKER 
GO
ALTER DATABASE [CatTocDi] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CatTocDi] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CatTocDi] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CatTocDi] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CatTocDi] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CatTocDi] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CatTocDi] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CatTocDi] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [CatTocDi] SET  MULTI_USER 
GO
ALTER DATABASE [CatTocDi] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CatTocDi] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CatTocDi] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CatTocDi] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CatTocDi] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [CatTocDi] SET QUERY_STORE = OFF
GO
USE [CatTocDi]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [CatTocDi]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 9/24/2018 9:21:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[AccountId] [int] NOT NULL,
	[Username] [varchar](50) NULL,
	[Password] [varchar](50) NULL,
	[Email] [varchar](255) NULL,
	[RoleId] [tinyint] NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[AccountId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Appointment]    Script Date: 9/24/2018 9:21:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Appointment](
	[AppointmentId] [int] NOT NULL,
	[CustomerId] [int] NULL,
	[SalonId] [int] NULL,
	[BookedDate] [date] NULL,
	[StartHour] [time](7) NULL,
	[EndHour] [time](7) NULL,
	[IsCompleted] [bit] NULL,
 CONSTRAINT [PK_Appointment] PRIMARY KEY CLUSTERED 
(
	[AppointmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[CustomerId] [int] NOT NULL,
	[AccountId] [int] NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[Phone] [varchar](20) NULL,
	[Gender] [bit] NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[RoleId] [tinyint] NOT NULL,
	[Name] [varchar](50) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[RoleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Salon]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Salon](
	[SalonId] [int] NOT NULL,
	[Name] [nvarchar](100) NULL,
	[Address] [varchar](255) NULL,
	[Phone] [varchar](20) NULL,
	[IsForMen] [bit] NULL,
	[IsForWomen] [bit] NULL,
	[RatingAverage] [float] NULL,
	[AccountId] [int] NULL,
 CONSTRAINT [PK_Salon] PRIMARY KEY CLUSTERED 
(
	[SalonId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SalonRegistration]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SalonRegistration](
	[RegistrationId] [int] NOT NULL,
	[SalonId] [int] NULL,
	[StartDate] [date] NULL,
	[EndDate] [date] NULL,
 CONSTRAINT [PK_SalonRegistration] PRIMARY KEY CLUSTERED 
(
	[RegistrationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SalonService]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SalonService](
	[SalonServiceId] [int] NOT NULL,
	[SalonId] [int] NULL,
	[ServiceId] [int] NULL,
	[Price] [float] NULL,
	[AvarageTime] [int] NULL,
 CONSTRAINT [PK_SalonService] PRIMARY KEY CLUSTERED 
(
	[SalonServiceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Schedule]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schedule](
	[ScheduleId] [int] NOT NULL,
	[RegistrationId] [int] NULL,
	[Date] [date] NULL,
 CONSTRAINT [PK_Schedule] PRIMARY KEY CLUSTERED 
(
	[ScheduleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Service]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Service](
	[ServiceId] [int] NOT NULL,
	[Name] [nvarchar](250) NULL,
	[Gender] [bit] NULL,
 CONSTRAINT [PK_Service] PRIMARY KEY CLUSTERED 
(
	[ServiceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ServiceAppointment]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ServiceAppointment](
	[AppointmentId] [int] NOT NULL,
	[ServiceId] [int] NOT NULL,
	[Price] [float] NULL,
	[Time] [int] NULL,
 CONSTRAINT [PK_ServiceAppointment] PRIMARY KEY CLUSTERED 
(
	[AppointmentId] ASC,
	[ServiceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TimeSlot]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TimeSlot](
	[SlotId] [int] NOT NULL,
	[ScheduleId] [int] NULL,
	[StartHour] [time](7) NULL,
	[EndHour] [time](7) NULL,
 CONSTRAINT [PK_TimeSlot] PRIMARY KEY CLUSTERED 
(
	[SlotId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 9/24/2018 9:21:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Voucher](
	[VoucherId] [int] NOT NULL,
	[VoucherCode] [varchar](50) NULL,
	[Description] [nvarchar](max) NULL,
	[SalonId] [int] NULL,
	[StartDate] [datetime] NULL,
	[EndDate] [datetime] NULL,
 CONSTRAINT [PK_Voucher] PRIMARY KEY CLUSTERED 
(
	[VoucherId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([RoleId])
GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FK_Account_Role]
GO
ALTER TABLE [dbo].[Appointment]  WITH CHECK ADD  CONSTRAINT [FK_Appointment_Customer] FOREIGN KEY([CustomerId])
REFERENCES [dbo].[Customer] ([CustomerId])
GO
ALTER TABLE [dbo].[Appointment] CHECK CONSTRAINT [FK_Appointment_Customer]
GO
ALTER TABLE [dbo].[Appointment]  WITH CHECK ADD  CONSTRAINT [FK_Appointment_Salon] FOREIGN KEY([SalonId])
REFERENCES [dbo].[Salon] ([SalonId])
GO
ALTER TABLE [dbo].[Appointment] CHECK CONSTRAINT [FK_Appointment_Salon]
GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD  CONSTRAINT [FK_Customer_Account] FOREIGN KEY([AccountId])
REFERENCES [dbo].[Account] ([AccountId])
GO
ALTER TABLE [dbo].[Customer] CHECK CONSTRAINT [FK_Customer_Account]
GO
ALTER TABLE [dbo].[Salon]  WITH CHECK ADD  CONSTRAINT [FK_Salon_Account] FOREIGN KEY([AccountId])
REFERENCES [dbo].[Account] ([AccountId])
GO
ALTER TABLE [dbo].[Salon] CHECK CONSTRAINT [FK_Salon_Account]
GO
ALTER TABLE [dbo].[SalonRegistration]  WITH CHECK ADD  CONSTRAINT [FK_SalonRegistration_Salon] FOREIGN KEY([SalonId])
REFERENCES [dbo].[Salon] ([SalonId])
GO
ALTER TABLE [dbo].[SalonRegistration] CHECK CONSTRAINT [FK_SalonRegistration_Salon]
GO
ALTER TABLE [dbo].[SalonService]  WITH CHECK ADD  CONSTRAINT [FK_SalonService_Salon] FOREIGN KEY([SalonId])
REFERENCES [dbo].[Salon] ([SalonId])
GO
ALTER TABLE [dbo].[SalonService] CHECK CONSTRAINT [FK_SalonService_Salon]
GO
ALTER TABLE [dbo].[SalonService]  WITH CHECK ADD  CONSTRAINT [FK_SalonService_Service] FOREIGN KEY([ServiceId])
REFERENCES [dbo].[Service] ([ServiceId])
GO
ALTER TABLE [dbo].[SalonService] CHECK CONSTRAINT [FK_SalonService_Service]
GO
ALTER TABLE [dbo].[Schedule]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_SalonRegistration] FOREIGN KEY([RegistrationId])
REFERENCES [dbo].[SalonRegistration] ([RegistrationId])
GO
ALTER TABLE [dbo].[Schedule] CHECK CONSTRAINT [FK_Schedule_SalonRegistration]
GO
ALTER TABLE [dbo].[ServiceAppointment]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAppointment_Appointment] FOREIGN KEY([AppointmentId])
REFERENCES [dbo].[Appointment] ([AppointmentId])
GO
ALTER TABLE [dbo].[ServiceAppointment] CHECK CONSTRAINT [FK_ServiceAppointment_Appointment]
GO
ALTER TABLE [dbo].[ServiceAppointment]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAppointment_SalonService] FOREIGN KEY([ServiceId])
REFERENCES [dbo].[SalonService] ([SalonServiceId])
GO
ALTER TABLE [dbo].[ServiceAppointment] CHECK CONSTRAINT [FK_ServiceAppointment_SalonService]
GO
ALTER TABLE [dbo].[TimeSlot]  WITH CHECK ADD  CONSTRAINT [FK_TimeSlot_Schedule] FOREIGN KEY([ScheduleId])
REFERENCES [dbo].[Schedule] ([ScheduleId])
GO
ALTER TABLE [dbo].[TimeSlot] CHECK CONSTRAINT [FK_TimeSlot_Schedule]
GO
ALTER TABLE [dbo].[Voucher]  WITH CHECK ADD  CONSTRAINT [FK_Voucher_Salon] FOREIGN KEY([SalonId])
REFERENCES [dbo].[Salon] ([SalonId])
GO
ALTER TABLE [dbo].[Voucher] CHECK CONSTRAINT [FK_Voucher_Salon]
GO
USE [master]
GO
ALTER DATABASE [CatTocDi] SET  READ_WRITE 
GO
