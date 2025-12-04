# Öğrenci Otomasyonu - Student Automation System

Spring Boot REST API tabanlı Öğrenci Yönetim Sistemi

## Özellikler

- **Kimlik Doğrulama**: JWT token tabanlı giriş sistemi
- **Rol Tabanlı Erişim Kontrolü**: Admin, Öğretmen ve Öğrenci rolleri
- **Kullanıcı Yönetimi**: Kullanıcı oluştur, güncelle, sil
- **Öğrenci Yönetimi**: Öğrenci profili oluştur ve yönet
- **Öğretmen Yönetimi**: Öğretmen profili oluştur ve yönet
- **Ders Yönetimi**: Dersler oluştur, güncelle, sil
- **Ders Kaydı**: Öğrenciler derslere kaydol, notu görüntüle
- **H2 In-Memory Veritabanı**: Test ve geliştirme için

## Teknoloji Stack

- Java 17
- Spring Boot 4.0.0
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- H2 Database
- Maven
- Lombok

## Başlangıç

### Gereksinimler

- Java 17 veya daha yeni sürüm
- Maven 3.6+

### Kurulum ve Çalıştırma

1. Proje dizinine gidin:
```bash
cd demo
```

2. Maven bağımlılıklarını yükleyin:
```bash
mvn clean install
```

3. Uygulamayı çalıştırın:
```bash
mvn spring-boot:run
```

Uygulama `http://localhost:8080` adresinde çalışacaktır.

## API Endpoints

### Kimlik Doğrulama

- `POST /api/auth/login` - Giriş yap (Token al)

### Admin - Kullanıcı Yönetimi

- `POST /api/admin/users` - Yeni kullanıcı oluştur
- `GET /api/admin/users` - Tüm kullanıcıları listele
- `GET /api/admin/users/{id}` - Kullanıcı detayını getir
- `GET /api/admin/users/role/{role}` - Role göre kullanıcıları filtrele
- `PUT /api/admin/users/{id}` - Kullanıcı bilgisini güncelle
- `DELETE /api/admin/users/{id}` - Kullanıcıyı sil (deaktive et)

### Öğrenci Yönetimi

- `POST /api/students` - Yeni öğrenci oluştur
- `GET /api/students` - Tüm öğrencileri listele
- `GET /api/students/{id}` - Öğrenci detayını getir
- `PUT /api/students/{id}` - Öğrenci bilgisini güncelle
- `DELETE /api/students/{id}` - Öğrenciyi sil

### Öğretmen Yönetimi

- `POST /api/teachers` - Yeni öğretmen oluştur
- `GET /api/teachers` - Tüm öğretmenleri listele
- `GET /api/teachers/{id}` - Öğretmen detayını getir
- `PUT /api/teachers/{id}` - Öğretmen bilgisini güncelle
- `DELETE /api/teachers/{id}` - Öğretmeni sil

### Ders Yönetimi

- `POST /api/courses` - Yeni ders oluştur
- `GET /api/courses` - Tüm dersleri listele
- `GET /api/courses/{id}` - Ders detayını getir
- `GET /api/courses/teacher/{teacherId}` - Öğretmenin derslerini listele
- `GET /api/courses/semester/{semester}` - Döneme göre dersleri filtrele
- `PUT /api/courses/{id}` - Ders bilgisini güncelle
- `DELETE /api/courses/{id}` - Dersi sil

### Ders Kaydı (Enrollment)

- `POST /api/enrollments` - Derse kaydol
- `GET /api/enrollments` - Tüm kayıtları listele
- `GET /api/enrollments/{id}` - Kayıt detayını getir
- `GET /api/enrollments/student/{studentId}` - Öğrencinin kayıtlarını listele
- `GET /api/enrollments/course/{courseId}` - Dersin kayıtlarını listele
- `PUT /api/enrollments/{id}/grade` - Not gir
- `DELETE /api/enrollments/{id}` - Dersi bırak

## Örnek Giriş Bilgileri

Sistem başlangıçta aşağıdaki örnek kullanıcılar ile yüklenmektedir:

### Admin
- Email: `admin@example.com`
- Password: `admin123`

### Öğretmen 1
- Email: `teacher1@example.com`
- Password: `teacher123`

### Öğretmen 2
- Email: `teacher2@example.com`
- Password: `teacher123`

### Öğrenci 1
- Email: `student1@example.com`
- Password: `student123`

### Öğrenci 2
- Email: `student2@example.com`
- Password: `student123`

## Giriş Yap ve Token Alma

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "admin123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "email": "admin@example.com",
  "role": "ADMIN"
}
```

## API Çağrılarında Token Kullanma

```bash
curl -X GET http://localhost:8080/api/students \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

## Rol Tabanlı Erişim Kontrolü

- **ADMIN**: Tüm işlemlere erişim
- **TEACHER**: Ders oluştur/yönet, not gir
- **STUDENT**: Kendi bilgilerini görüntüle, derslere kaydol, notlarını görüntüle

## H2 Konsolu

Veritabanını görüntülemek için:
```
URL: http://localhost:8080/h2-console
Driver: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (boş)
```

## Proje Yapısı

```
src/main/java/com/example/demo/
├── controller/          # REST API Denetleyicileri
├── dto/                 # Data Transfer Objects
├── entity/              # JPA Varlıkları
├── repository/          # Spring Data JPA Repository'leri
├── security/            # JWT ve Güvenlik Yapılandırması
├── service/             # İş Mantığı Servisleri
├── DataLoader.java      # Örnek Verileri Yükleme
└── DemoApplication.java # Ana Uygulama Sınıfı
```

## Lisans

Açık kaynak - MIT Lisansı altında

## Geliştirici Notları

- Tüm şifreler BCrypt ile şifrelenmektedir
- JWT token varsayılan olarak 24 saat geçerlidir
- H2 veritabanı her uygulama başlatılışında sıfırlanır (geliştirme amaçlı)
