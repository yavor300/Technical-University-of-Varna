#include <iostream>
#include <string>
#include <Windows.h>
#include <fstream>
#include <iomanip>
using namespace std;

/*
* Представяне на компютърните конфигурации чрез
* структурите Processor и Computer
*/
struct Processor
{
    string manufacturer;
    string model;
    double frequency;
    int cores;
};

struct Computer
{
    string id;
    string brand;
    string model;
    Processor processor;
    double ram;
    double price;
    string available_status;
};

/*
* Константи съдържащи различните меню опции и
* стойности по условието на задачата
*/
const int MAX_NUMBER_OF_CONFIGURATIONS = 100;
const int INITIAL_CONFIGURATIONS_COUNT = 0;
const int DIGITS_TO_PRINT_AFTER_DECIMAL_POINT = 2;

const int ADD_NEW_CONFIGURATION_CHOICE = 1;
const int PRINT_ALL_CONFIGURATIONS_CHOICE = 2;
const int PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY = 3;
const int PRINT_CONFIGURATIONS_BY_BRAND_CHOICE = 4;
const int EDIT_CONFIGURATION_CHOICE = 5;
const int SELL_CONFIGURATION_CHOICE = 6;
const int MAKE_CONFIGURATIONS_AUDIT_CHOICE = 7;
const int EXIT_FROM_MENU_CHOICE = 8;

const int UPDATE_CONFIGURATION_ID_CHOICE = 1;
const int UPDATE_CONFIGURATION_BRAND_CHOICE = 2;
const int UPDATE_CONFIGURATION_MODEL_CHOICE = 3;
const int UPDATE_CONFIGURATION_RAM_CHOICE = 4;
const int UPDATE_CONFIGURATION_PRICE_CHOICE = 5;
const int UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE = 6;
const int UPDATE_CONFIGURATION_PROCESSOR_CHOICE = 7;
const int EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE = 8;

const int SELL_BY_SERIAL_NUMBER_CHOICE = 1;
const int SELL_BY_REQUIREMENTS_CHOICE = 2;
const int EXIT_FROM_SELLING_MENU_CHOICE = 3;

const int UPDATE_PROCESSOR_MANUFACTURER_CHOICE = 1;
const int UPDATE_PROCESSOR_MODEL_CHOICE = 2;
const int UPDATE_PROCESSOR_FREQUENCY_CHOICE = 3;
const int UPDATE_PROCESSOR_CORES_COUNT_CHOICE = 4;
const int EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE = 5;

const int PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE = 1;
const int PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE = 2;
const int PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE = 3;
const int EXIT_FROM_AUDIT_MENU_CHOICE = 4;

const string CONFIGURATION_IN_SELL_STATUS = "в продажба";
const string CONFIGURATION_SOLD_STATUS = "продадена";

/*
* Прототипи на всички функции, подредени по ред на извикване
*/
// прочиане и записване на конфигурациите в масив
void read_configurations_from_file(Computer configurations[], int& present_configurations_count);
// прочитане на валидна целочислена стойност
void read_valid_integer_value(int& value);
// валидиране на желаният брой конфигурации за добавяне
bool is_configurations_count_valid(int configurations_count, int present_configurations_count);
// проверка за празен string
bool is_string_empty(string& input);
// прочитане на валидна реална числена стойност
void read_valid_double_value(double& value);
// прочитане, валидиране на данните на процесора и запазването им в подадените променливи
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
// проверка дали конфигурация с даден сериен номер (id) съществува
bool configuration_exists_by_id(string& id, Computer configurations[], int present_configurations_count);
// прочитане, валидиране на данните на конфигурацията и запазването им в подадените променливи
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& available_status,
    Computer configurations[], int present_configurations_count);
// добавяне на една конфигурация
void add_configuration(Computer configurations[], int& present_configurations_count, string& processor_manufacturer,
    string& processor_model, string& computer_id, string& computer_brand, string& computer_model, string& available_status,
    double& processor_frequency, double& computer_ram, double& computer_price, int& processor_cores);
// добавяне на нови компютърни конфигурации
void process_add_configurations_request(Computer configurations[], int& present_configurations_count);
// извеждане на информация за една конфигурация
void print_configuration(Computer& computer);
// извеждане на информация за всички записани конфигурации
void print_all_configurations(Computer configurations[], int present_configurations_count);
// функционалност за извеждане на всички запазени конфигурации с валидация
void process_print_all_configurations_request(Computer configurations[], int present_configurations_count);
// сравняване на string-ове без значение от голяма и малка буква
bool compare_strings_case_insensitive(string first, string second);
// извеждане на всички конфигурации от дадена марка
void print_configurations_by_brand(Computer configurations[], int present_configurations_count, string& brand);
// функционалност за извеждане с валидация на всички запазени конфигурации от дадена марка
void process_print_configurations_by_brand_request(Computer configurations[], int present_configurations_count);
// намиране на най-голямата честота на процесор
double find_max_processor_frequency(Computer configurations[], int present_configurations_count);
// извеждане на конфигурации с дадена честота на процесора
void print_configuraions_by_processor_frequency(Computer configurations[], int present_configurations_count,
    double processor_frequency);
// функционалност за извеждане с валидация на всички запазени конфигурации с дадена честоа на процесора
void print_configurations_with_highest_processor_frequency(Computer configurations[], int present_configurations_count);
// проверка дали конфигурацията е налична за продажба
bool is_configuration_available(string& id, Computer configurations[], int present_configurations_count);
// взимане на конфигуация по сериен номер
Computer get_configuration_by_id(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на сериен номер на конфигурация
void update_configuration_id(string& old_id, Computer configurations[], int present_configurations_count);
// актуализиране на марка на конфигурация
void update_configuration_brand(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на модел на конфигурация
void update_configuration_model(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на RAM стойност на конфигурация
void update_configuration_ram(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на цена на конфигурация
void update_configuration_price(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на наличен статус на конфигурация
void update_configuration_status(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на производителя на процесора
void update_processor_manufacturer(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на модела на процесора
void update_processor_model(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на честотата на процесора
void update_processor_frequency(string& id, Computer configurations[], int present_configurations_count);
// актуализиране на броят ядра на процесора
void update_processor_cores(string& id, Computer configurations[], int present_configurations_count);
// фукционалност за актуализиране на елемент на процесор
void update_processor(string& configuration_id, Computer configurations[], int present_configurations_count);
//  фукционалност за актуализиране на елемент по конфигурацията
void update_configuration(Computer configurations[], int present_configurations_count);
// промяна на наличния статус на конфигурация към продадена
void change_configuration_availability_status_to_sold(string& id, Computer configurations[], int present_configurations_count);
// осъществяване на продажба по сериен номер
void sell_configuration_by_id(Computer configurations[], int present_configurations_count);
// прочитане на желаните характеристики на процесора при продажба
void read_processor_selling_data(string& manufacturer, string& model, string& frequency, string& cores);
// прочитане на желаните характеристики на компютъра при продажба
void read_computer_selling_data(string& brand, string& model, string& ram, string& price);
// намиране на подходящи конфигурации по посочени характеристики
void find_computers_with_requirements(Computer configurations[], int present_configurations_count,
    Computer found_configurations[], int& found_configurations_count, string& processor_manufacturer,
    string& processor_model, string& processor_frequency, string& processor_cores, string& computer_brand,
    string& computer_model, string& computer_ram, string& computer_price, int& selected_features, int& actual_features);
// осъществяване на продажба по желани характеристики
void sell_configuration_by_requirements(Computer configurations[], int present_configurations_count);
// функционалност за осъществяване на продажба - подменю и валидации
void sell_configuration(Computer configurations[], int present_configurations_count);
// проверка за съществуваща конфигурация по наличен статус
bool search_for_configurations_by_availability_status(Computer configurations[], int present_configurations_count,
    string available_status);
// сравняване на две чеисла и връщане на по-малкото
int find_min_number(int first, int second);
// сортиране на конфигурации по сериен номер
void sort_configurations_by_id(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
// принтиране на конфигурации с даден наличен статус
void print_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status);
// принтиране на конфигурации с даден наличен статус, сортирани по сериен номер
void print_available_configurations_sorted_by_id(Computer configurations[], int present_configurations_count);
// проверка за съществуваща конфигурация по модел процесор и рам стойност
bool configuration_exists_by_processor_model_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram);
// сортиране на конфигурациите по цена в низходящ ред
void sort_configurations_by_price_desc(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
// извеждане на конфигирации с даден модел на процесора и RAM
void print_configurations_by_brand_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram);
// извеждане на конфигирации с даден модел на процесора и RAM сортирани по цена в низходящ ред
void print_configurations_by_brand_and_ram_sorted_by_price_desc(Computer configurations[], int present_configurations_count);
// сортиране на конфигурации по модел на процесора
void sort_configurations_by_processor_model(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
// извеждане на продадените конфигурации сортирани по модел на процесора
void print_unavailable_configurations_sorted_by_processor_model(Computer configurations[], int present_configurations_count);
// осъществяване на одит чрез подменю
void make_configurations_audit(Computer configurations[], int present_configurations_count);
// записване на конфигурациите във файл
void store_configurations_in_file(Computer configurations[], int present_configurations_count);

/*
* Менюто с избор на функциите в програмата е реализирано в main функцията.
* За имплементацията му е използван do-while цикъл.
* На всяка итерация на екрана се извежда съобщение с всички възможни дейстивия
* на програмта и клавишът, който трябва да се натисне, за да се изпълнят.
* След това се извежда съобщение, което подканва потребителят да въведе своя избор.
* Въвеждането на избор е реализирано с функцията read_valid_integer_value.
*/
int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int menu_choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    cout << "===== ИНФОРМАЦИОННА СИСТЕМА ЗА ПРОДАЖБА НА КОМПЮТЪРНИ КОНФИГУРАЦИИ =====\nРазработчик: Явор Чамов\n\n";

    read_configurations_from_file(configurations, present_configurations_count);
    
    do
    {
        printf("===== НАЧАЛНО МЕНЮ =====\n\nВъведете %d, за да добавите нова конфигурация.\nВъведете %d, за да изведете всички конфигурации.\nВъведете %d, за да изведете конфигурациите с най-висока тактова честота на процесора.\nВъведете %d, за да изведете конфигурации от избрана марка.\nВъведете %d, за да редактирате конфигурация.\nВъведете %d, за да осъществите продажба.\nВъведете %d, за да направите одит на конфигурациите.\nВъведете %d, за да запазите съществуващите конфигурации във файл и да спрете програмата.\n",
            ADD_NEW_CONFIGURATION_CHOICE, PRINT_ALL_CONFIGURATIONS_CHOICE, PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY, PRINT_CONFIGURATIONS_BY_BRAND_CHOICE, EDIT_CONFIGURATION_CHOICE, SELL_CONFIGURATION_CHOICE, MAKE_CONFIGURATIONS_AUDIT_CHOICE, EXIT_FROM_MENU_CHOICE);
        
        /*
        * Валидирането на меню избор е реализирано с do-while цикъл,
        * който прекратява изпълнението си при въведен избор в посочения интервал
        * [(първа опция в менюто) – (последна опция в менюто)].
        */
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < ADD_NEW_CONFIGURATION_CHOICE || menu_choice > EXIT_FROM_MENU_CHOICE);

        /*
        * След правилно въведена и валидна меню опция програмата зачиства текста
        * от екрана и чрез конструкцията switch се задейства функционалността,
        * отговаряща на посочената меню опция.
        * След като приключи изпълнението на избраната функционалност се извежда празен ред в конзолата
        * и отново се визуализира началното меню.
        */
        system("cls");
        switch (menu_choice)
        {
        case ADD_NEW_CONFIGURATION_CHOICE:
            process_add_configurations_request(configurations, present_configurations_count);
            break;

        case PRINT_ALL_CONFIGURATIONS_CHOICE:
            process_print_all_configurations_request(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_CHOICE:
            process_print_configurations_by_brand_request(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY:
            print_configurations_with_highest_processor_frequency(configurations, present_configurations_count);
            break;

        case EDIT_CONFIGURATION_CHOICE:
            update_configuration(configurations, present_configurations_count);
            break;

        case SELL_CONFIGURATION_CHOICE:
            sell_configuration(configurations, present_configurations_count);
            break;

        case MAKE_CONFIGURATIONS_AUDIT_CHOICE:
            make_configurations_audit(configurations, present_configurations_count);
            break;
        }
        cout << endl;
        // Ако потребителят е избрал последната меню опция, програмата прекратява своето изпълнение
    } while (menu_choice != EXIT_FROM_MENU_CHOICE);

    store_configurations_in_file(configurations, present_configurations_count);
}

/*
* Функцията read_configurations_from_file прочира конфигурациите от файл и ги записва
* в масива за съхранение на информация. Приема като параметри масива за записване на
* конфигурациите и броят на запазените конфиграции. Дефинира променлива от тип fstream
* и отваря файл с име configuratios.dat. Ако файлът не се отвори успешно
* се извежда съобщение за грешка и фунцкията прекратява изпъленинието си.
* При успешно отваряне на файла, той се прочита целия като за всяка записана
* конфигурация се създава променлива от тип структура Computer и се добавя към масива.
* Накрая четенето от файла се затваря и се извежда броят на конфигурациите прочетени от файла.
*/
void read_configurations_from_file(Computer configurations[], int& present_configurations_count)
{
    fstream configurations_data;
    configurations_data.open("configurations.dat", ios::in);

    if (configurations_data.fail())
    {
        cout << "Няма запазени конфигурации, които да бъдат прочетени от файл.\n\n";
        return;
    }

    while (!configurations_data.eof())
    {
        Computer computer;
        Processor processor;

        string configuration_id, configuration_brand, configuration_model, configuration_ram, processor_manufacturer,
            processor_model, processor_frequency, processor_cores, configuration_price, configuration_availability;

        getline(configurations_data, configuration_id);
        computer.id = configuration_id.substr(14, configuration_id.length() - 14);

        getline(configurations_data, configuration_brand);
        computer.brand = configuration_brand.substr(7, configuration_brand.length() - 7);

        getline(configurations_data, configuration_model);
        computer.model = configuration_model.substr(7, configuration_model.length() - 7);

        getline(configurations_data, configuration_ram);
        computer.ram = stod(configuration_ram.substr(11, configuration_ram.length() - 14));

        getline(configurations_data, processor_manufacturer);
        getline(configurations_data, processor_manufacturer);
        processor.manufacturer = processor_manufacturer.substr(15, processor_manufacturer.length() - 15);

        getline(configurations_data, processor_model);
        processor.model = processor_model.substr(8, processor_model.length() - 8);

        getline(configurations_data, processor_frequency);
        processor.frequency = stod(processor_frequency.substr(18, processor_frequency.length() - 22));

        getline(configurations_data, processor_cores);
        processor.cores = stoi(processor_cores.substr(12, processor_cores.length() - 12));

        getline(configurations_data, configuration_price);
        computer.price = stod(configuration_price.substr(6, configuration_price.length() - 10));

        getline(configurations_data, configuration_availability);
        computer.available_status = configuration_availability.substr(16, configuration_availability.length() - 16);

        configurations_data.ignore();

        computer.processor = processor;
        configurations[present_configurations_count++] = computer;
    }

    configurations_data.close();
    printf("%d конфигурации бяха прочетени от файл.\n\n", present_configurations_count);
}

/*
* Функцията read_valid_integer_value приема като входен параметър адреса
* на променлива от тип int и гарантира, че потребителят е въвел валидна числена стойност.
* Реализирана е с безкраен while цикъл, който прекратява своето изпълнение
* при въведена валидна числена стойност.
* В случай на невалидни входни данни на екрана се извежда съобщение,
* което приканва потребителят да въведе валидна числена стойност.
* Стойността се запазва в адреса на подаденият параметър value.
*/
void read_valid_integer_value(int& value)
{
    while (true) {
        if (cin >> value) {
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
            break;
        }
        else {
            cout << "Въведете валидна числена стойност: ";
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
        }
    }
}

/*
* Функцията is_configurations_count_valid връща булев резултат.
* Приема като входни параметри желаният брой конфигурации за добавяне и броят на текущите конфигурации.
* Ако броят на желаните конфигурации за добавяне е по-голям от 0
* и има място за тяхното съtrхранение, резултатът от фунцкията е true. В противен случай – false.
*/
bool is_configurations_count_valid(int configurations_count, int present_configurations_count)
{
    return configurations_count > 0 &&
        MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count >= configurations_count;
}

/*
* Функцията is_string_empty връща булев резултат.
* Приема като параметът променлива от тип string.
* Ако резултатът от функцията empty на класа string е true, функцията връща true.
* В противен случай се string-ът се обхожда и проверява дали съществува символ
* различен от интервал и таб. Ако такъв символ съществува функцията връща false.
* Ако не е намерен символ различен от споменатите, функцията връща true.
*/
bool is_string_empty(string& input)
{
    if (input.empty()) return true;
    for (int i = 0; i < input.size(); i++)
    {
        if (input[i] != ' ' && input[i] != '\t') return false;
    }
    return true;
}

/*
* Функцията read_valid_double_value приема като входен параметър адреса
* на променлива от тип double и гарантира, че потребителят е въвел
* валидна числена стойност.
* Реализирана е с безкраен while цикъл, който прекратява своето изпълнение
* при въведена валидна числена стойност. В случай на невалидни входни данни
* на екрана се извежда съобщение, което приканва потребителят да въведе валидна числена стойност.
* Стойността се запазва в адреса на подаденият параметър value.
*/
void read_valid_double_value(double& value)
{
    while (true) {
        if (cin >> value) {
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
            break;
        }
        else {
            cout << "Въведете валидна числена стойност: ";
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
        }
    }
}

/*
* Функцията read_processor_data приема като параметри променливи (подадени по адрес),
* в които ще бъдат записани данните за всеки елемент на процесора.
* Посредством do-while цикъл и функцията is_string_empty на потребителят
* не му се позволява да въведе празна стойност за променливите от тип string.
* Прочитането на стойност за честотата на процесора се извършва чрез фунцкията read_valid_double_value.
* Стойностите за честота на процесора и броят ядра не могат да бъдат по-малки от 0.
* Валидацията е направена чрез while цикъл.
*/
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores)
{
    do
    {
        cout << "Въведете производител на процесора: ";
        getline(cin, manufacturer);
    } while (is_string_empty(manufacturer));

    do
    {
        cout << "Въведете модел на процесора: ";
        getline(cin, model);
    } while (is_string_empty(model));

    cout << "Въведете честота на процесора (GHz): ";
    read_valid_double_value(frequency);
    while (frequency <= 0)
    {
        cout << "Честотата трябва да е по-голяма от 0.\nВъведете честота на процесора (GHz): ";
        read_valid_double_value(frequency);
    }

    cout << "Въведете брой ядра на процесора: ";
    read_valid_integer_value(cores);
    while (cores <= 0)
    {
        cout << "Броят ядра трябва да е по-голям от 0.\nВъведете брой ядра на процесора: ";
        read_valid_integer_value(cores);
    }
}

/*
* Функцията configuration_exists_by_id връща булев резултат.
* Приема като входни параметри серийният номер на конфигурацията,
* масив с всички записани конфигурации и броят на записаните конфигурации.
* Итерира през масива и за всяка конфигурация проверява дали нейният сериен номер
* е равен на подадения като параметър. Ако това условие е вярно,
* функцията връща true, в противен случай – false.
*/
bool configuration_exists_by_id(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return true;
    }
    return false;
}

/*
* Функцията read_computer_data приема като параметри променливи (подадени по адрес),
* в които ще бъдат записани данните за всеки елемент на компютъра.
* Посредством do-while цикъл и функцията is_string_empty на потребителят не му се
* позволява да въведе празна стойност за променливите от тип string.
* Допълнително при въвеждането на сериен номер (id) се прави проверка дали вече
* съществува конфигурация с този сериен номер чрез configuration_exists_by_id.
* Ако съществува, потребителят трябва да въведе нов сериен номер на конфигурацията.
* Прочитането на стойност за RAM и цена се извършва чрез фунцкията read_valid_double_value.
* Стойностите за RAM и цена не могат да бъдат по-малки от 0.
* Валидацията е направена чрез while цикъл.
* Наличният статус на конфигурацията не може да бъде различен от „в продажба/продадена“.
* Валидацията е наравена чрез do-while цикъл.
*/
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& available_status, Computer configurations[], int present_configurations_count)
{
    do
    {
        cout << "Въведете сериен номер на компютъра: ";
        getline(cin, id);
        if (configuration_exists_by_id(id, configurations, present_configurations_count))
        {
            cout << "Вече съществува конфигурация с този сериен номер!\n";
        }
    } while (is_string_empty(id) || configuration_exists_by_id(id, configurations, present_configurations_count));

    do
    {
        cout << "Въведете марка на компютъра: ";
        getline(cin, brand);
    } while (is_string_empty(brand));

    do
    {
        cout << "Въведете модел на компютъра: ";
        getline(cin, model);
    } while (is_string_empty(model));

    cout << "Въведете RAM на компютъра (GB): ";
    read_valid_double_value(ram);
    while (ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете RAM на компютъра (GB): ";
        read_valid_double_value(ram);
    }

    cout << "Въведете цена на компютъра: ";
    read_valid_double_value(price);
    while (price <= 0)
    {
        cout << "Цената трябва да е по-голяма от 0.\nВъведете цена на компютъра: ";
        read_valid_double_value(price);
    }

    do
    {
        printf("Наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
        getline(cin, available_status);
    } while (available_status != CONFIGURATION_IN_SELL_STATUS && available_status != CONFIGURATION_SOLD_STATUS);
}

/*
* Функцията add_configuration има за цел да добави една конфигурация към масива,
* който съдържа всички запазени конфигурации.
* Като входни параметри приема:
*    масив от тип структура Computer
*	 брой запазени конфигурации
*    променливи (подадени по адрес), в които ще бъдат записани данните за всеки елемент на конфигурацията
* Чрез функциите read_processor_data и read_computer_data се присвояват стойности на подадените по адрес
* променливи за данните на конфиурацията.
* След това се създават променливи от тип Processor и Computer със съответните стойности.
* Променливата от тип Computer се добавя към масива с конфигурации.
* Броят на конфигурациите в масива се увеличава с 1.
*/
void add_configuration(Computer configurations[], int& present_configurations_count, string& processor_manufacturer,
    string& processor_model, string& computer_id, string& computer_brand, string& computer_model, string& available_status,
    double& processor_frequency, double& computer_ram, double& computer_price, int& processor_cores)
{
    cout << endl << "===== ИНФОРМАЦИЯ ЗА ПРОЦЕСОР =====" << endl;
    read_precessor_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
    Processor processor = { processor_manufacturer, processor_model, processor_frequency, processor_cores };

    cout << endl << "===== ИНФОРМАЦИЯ ЗА КОМПЮТЪР =====" << endl;
    read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, available_status, configurations, present_configurations_count);
    Computer computer = { computer_id, computer_brand, computer_model, processor, computer_ram, computer_price, available_status };

    configurations[present_configurations_count++] = computer;

    cout << "\nКонфигурацията е добавена успешно!\n";
}

/*
* Функционалността за добавяне на нови компютърни конфигурации
* е реализирана във функцията process_add_configurations_request.
* Приема като входни параметри масив с всички запазени конфигурации и брой запазени конфигурации.
* При нейното извикване се извежда текст показващ името на избраната функционалност,
* както и текст приканващ потребителя да въведе брой конфигурации за добавяне.
* След това се извършва валидация на въведения брой конфигурации.
* В случай, че броят на желаните конфигурации за добавяне не е валиден,
* на екрана се извежда подходящо съобщение.
* При невалиден брой конфигурации за добавяне, функцията приключва изпълнението си.
*/
void process_add_configurations_request(Computer configurations[], int& present_configurations_count)
{
    cout << "===== ДОБАВЯНЕ НА НОВИ КОНФИГУРАЦИИ =====\n\nВъведете брой конфигурации за добавяне: ";
    int configurations_count;
    read_valid_integer_value(configurations_count);

    if (!is_configurations_count_valid(configurations_count, present_configurations_count))
    {
        cout << "\nВъведеният брой конфигурации не е валиден.";
        int remaining_slots = MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count;
        if (remaining_slots < configurations_count) printf("\nИма място за %d конфигурации.\n", remaining_slots);
        else printf("\nБроят конфигурации трябва да е в интервала: [%d - %d]\n", 1, remaining_slots);
        return;
    }

    /*
    * При валиден брой конфигурации за добавяне се декларират променливи,
    * в които ще се запише информация за всяка част от конфигурацията.
    */
    string processor_manufacturer, processor_model, computer_id, computer_brand, computer_model, available_status;
    double processor_frequency, computer_ram, computer_price;
    int processor_cores;

    /*
    * Посредством цикъл се итерира през броя конфигурации за добавяне.
    * Във всяка итерация се извиква фунцкията add_configuration. 
    */
    for (int i = 0; i < configurations_count; i++)
    {
        add_configuration(configurations, present_configurations_count, processor_manufacturer, processor_model,
            computer_id, computer_brand, computer_model, available_status, processor_frequency, computer_ram, computer_price,
            processor_cores);

        /*
        * Ако циклът не е достигнал до последната конфигурация на екрана се извежда съобщение,
        * което пита потребителят дали иска да продължи с добавянето на конфигурации.
        * За да продължи с добавянето на конфигурации трябва да въведе y/Y,
        * а за да прекрати добавянето на конфигурации n/N.
        * Входът на избора е валидиран чрез while цикъл.
        * При въвеждане на /N се извежда съобщение и функцията спира своето изпълнение.
        */
        if (i != configurations_count - 1)
        {
            cout << "\nЩе продължите ли с въвеждането на конфигурации? (Y/N): ";
            char confirm;
            cin >> confirm;
            while (tolower(confirm) != 'y' && tolower(confirm) != 'n')
            {
                cout << "Въведете Y, за да продължите с въвежането.\nВъведете N, за да прекратите въвеждането.\nВъведете избор (Y/N): ";
                cin >> confirm;
            }
            cin.ignore();

            if (tolower(confirm) == 'n')
            {
                cout << "\nПрекратяване на въвеждането на конфигурации.\n";
                break;
            }
        }
    }
}

/*
* Функцията print_configuration извежда информация за една конфигурация.
* Приема като входен параметър конфигурация, чиято информация трябва да бъде изведена на монитора.
*/
void print_configuration(Computer& computer)
{
    Processor processor = computer.processor;
    printf("\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f GB\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f GHz\n\tБрой ядра: %d\nЦена: %.2f лв.\nНаличен статус: %s\n",
        computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.available_status.c_str());
}

/*
* Функцията print_all_configurations извежда информация за всички записани конфигурации.
* Приема като входен параметър масив с всички запазени конфигурации и брой запазени конфигурации.
* Итерира през масива с конфигурации и при всяка итерация извиква print_configuration с текущата конфигурация.
*/
void print_all_configurations(Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        print_configuration(configurations[i]);
    }
}

/*
* Функционалността за извеждане на всички конфигурации.
* Приема като входен параметър масив с всички запазени конфигурации 
* и брой запазени конфигурации. Извежда информативно съобщение на екрана.
* Ако броят на запазените конфигурации е 0, извежда съобщени на екрана и
* приключва изпълнение. В противен случай, извиква фунцкията print_all_configuraions.
*/
void process_print_all_configurations_request(Computer configurations[], int present_configurations_count)
{
    printf("===== ИНФОРМАЦИЯ ЗА ВСИЧКИ КОНФИГУРАЦИИ (%d) =====\n", present_configurations_count);

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации." << endl;
        return;
    }

    print_all_configurations(configurations, present_configurations_count);
}

/*
* Функцията compare_strings_case_insensitive сравнява две string стойности без значение дали
* буквата е малка или главна. Приема като входни параметри две string стойности и връща булев резултат.
* Ако дължината на string-овете е различна връща false и прекратява изпълнение.
* В противен случай обхожда първият string и сравнява символите му със
* символите от втория низ чрез функцията tolower. Ако има различен символ връща false, в противен случай – true.
*/
bool compare_strings_case_insensitive(string first, string second)
{
    if (first.length() != second.length()) return false;

    for (int i = 0; i < first.length(); i++)
    {
        if (tolower(first[i]) != tolower(second[i])) return false;
    }

    return true;
}

/*
* Функцията print_configurations_by_brand извежда всички конфигурации с дадена марка.
* Приема като входни параметри масив с всички запазени конфигурации, брой запазени конфигурации
* и името на марката. Дефинира булева променлива, която е флаг за това дали са намерени конфигурации
* от търсената марка. Функцията итерира през всички запазени конфигурации и проверява дали марката
* на текущата конфигурацията е същата като въведената. Сравняването става чрез  compare_strings_case_insensitive.
8 Ако марките са равни конфигурацията се извежда чрез print_configuration и булевата променлива
* присвоява стойност true. Ако не са намерени конфигурации с марката на екрана се извежда съобщение.
*/
void print_configurations_by_brand(Computer configurations[], int present_configurations_count, string& brand)
{
    bool is_brand_existing = false;
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        if (compare_strings_case_insensitive(computer.brand, brand))
        {
            print_configuration(configurations[i]);
            is_brand_existing = true;
        }
    }
    if (!is_brand_existing) printf("\nКонфигурации с марката %s не са намерени.\n", brand.c_str());
}

/*
* Функционалността за извеждане на всички конфигурации от дадена марка
* е реализирана във функцията process_print_configurations_by_brand_request.
* Приема като входен параметър масив с всички запазени конфигурации и брой запазени конфигурации.
* Извежда информативно съобщение на екрана. Ако броят на запазените конфигурации е 0,
* извежда съобщени на екрана и приключва изпълнение. В противен случай се дефинира
* string променлива за името на марката. Потребителят въвежда марка като входът е валидиран
* чрез do-while цикъл и is_string_empty.
* Накрая се извиква функцията print_configurations_by_brand с въведената марка.
*/
void process_print_configurations_by_brand_request(Computer configurations[], int present_configurations_count)
{
    cout << "===== ИЗВЕЖДАНЕ НА КОНФИГУРАЦИИТЕ ОТ ДАДЕНА МАРКА =====\n\n";

    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации.\n";
        return;
    }

    string brand;
    do
    {
        cout << "Въведете марка, за която искате да намерите конфигурации: ";
        getline(cin, brand);
    } while (is_string_empty(brand));
    
    print_configurations_by_brand(configurations, present_configurations_count, brand);
}

/*
* Функцията find_max_processor_frequency намира най-голямата честота на процесор от запазените конфигурации.
* Приема като входни параметри масив с всички запазени конфигурации,
* брой запазени конфигурации и името на марката. Връща като резултат double стойност.
* Дефинира double променлива и й присвоява честота на първият процесор. След това итерира
* през останалите конфигурации и проверява дали съществува процесор с по-голяма честота.
* Ако такъв съществува, стойността на променливата се актуализира. Накрая се връща стойността на променливата.
*/
double find_max_processor_frequency(Computer configurations[], int present_configurations_count)
{
    double max = configurations[0].processor.frequency;
    for (int i = 1; i < present_configurations_count; i++)
    {
        if (configurations[i].processor.frequency > max) max = configurations[i].processor.frequency;
    }
    return max;
}

/*
* Функцията print_configurations_by_processor_frequency извежда всички конфигурации
* с дадена честота на процесора. Приема като входни параметри масив с всички запазени
* конфигурации, брой запазени конфигурации и желаната честота на процесора.
* Функцията итерира през всички запазени конфигурации и проверява дали честота на текущата
* конфигурация е равна на подадената като параметър. Ако честотите са равни конфигурацията
* се извежда чрез print_configuration.
*/
void print_configuraions_by_processor_frequency(Computer configurations[], int present_configurations_count, double processor_frequency)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].processor.frequency == processor_frequency) print_configuration(configurations[i]);
    }
}

/*
* Функционалността за извеждане на всички конфигурации с дадена честота на процесора
* е реализирана във функцията print_configurations_with_highest_processor_frequency.
* Приема като входен параметър масив с всички запазени конфигурации и брой запазени конфигурации.
* Извежда информативно съобщение на екрана. Ако броят на запазените конфигурации е 0,
* извежда съобщени на екрана и приключва изпълнение.
* В противен случай се дефинира double променлива и й се присвоява най-високата честота на процесор
* чрез find_max_processor_frequency. След това се извеждат конфигурациите
* чрез функцията print_configurations_by_processor_frequency.
*/
void print_configurations_with_highest_processor_frequency(Computer configurations[], int present_configurations_count)
{
    cout << "===== ИЗВЕЖДАНЕ НА КОНФИГУРАЦИИТЕ С НАЙ-ГОЛЯМА ТАКТОВА ЧЕСТОТА НА ПРОЦЕСОРА =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    double highest_processor_frequency = find_max_processor_frequency(configurations, present_configurations_count);
    print_configuraions_by_processor_frequency(configurations, present_configurations_count, highest_processor_frequency);
}

/*
* Функцията is_configuration_available проверява дали дадена конфигурация е налична за продажба.
* Връща булев резултат. Приема като параметри серийният номер на конфигурацията,
* масив с всички запазени конфигурации, брой запазени конфигурации.
* Обхожда всички конфигурации и проверява дали серийният номер на текущата конфигурация
* е равен на подаденият като параметър.
* Ако условието е вярно, се прави проверка дали наличният статус е равен на „в продажба“.
* Връща true ако конфигурацията е налична и false ако конфигурацията не е налична.
*/
bool is_configuration_available(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i].available_status == CONFIGURATION_IN_SELL_STATUS;
    }
    return false;
}

/*
* Функцията get_configuration_by_id се използва за взимане на конфигурация по сериен номер.
* Приема като параметри серийният номер на конфигурацията,
* масив с всички запазени конфигурации, брой запазени конфигурации.
* Обхожда всички конфигурации и проверява дали серийният номер на текущата
* конфигурация е равен на подаденият като параметър. Ако условието е вярно, се връща текущата конфигурация.
*/
Computer get_configuration_by_id(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i];
    }
}

/*
* Функцията update_configuration_id се използва за актуализиране на серийният номер на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип string за новият сериен номер.
* Входът за серийнят номер е валидиран чрез do-while цикъл и функцията is_string_empty.
* След въвеждане на нов сериен номер се прави проверка дали съществува конфигурация с този номер.
* Ако съществува такава конфигурация се извежда съобщение и фунцкията прилючва изпълнението си.
* Ако конфигурацията не съществува се итерира през всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията серийният й номер се заменя с нововъведения.
* Променливата, която е съхранявала старият сериен номер присвоява новият такъв.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_id(string& old_id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА СЕРИЕН НОМЕР НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(old_id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string updated_id;
    do
    {
        cout << "Въведете нов сериен номер на кофигурацията: ";
        getline(cin, updated_id);
    } while (is_string_empty(updated_id));

    if (configuration_exists_by_id(updated_id, configurations, present_configurations_count))
    {
        cout << "\nВече съществува конфигурация с този сериен номер.\n";
        return;
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(old_id) == 0)
        {
            configurations[i].id = updated_id;
            old_id = updated_id;
            cout << "\nСерийният номер е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_configuration_brand се използва за актуализиране на марката на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип string за новата марка.
* Входът за марката е валидиран чрез do-while цикъл и функцията is_string_empty.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията марката й се заменя с нововъведената.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_brand(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МАРКА НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_brand;
    do
    {
        cout << "Въведете нова марка на конфигурацията: ";
        getline(cin, new_brand);
    } while (is_string_empty(new_brand));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].brand = new_brand;
            cout << "\nМарката е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_configuration_model се използва за актуализиране на модела на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип string за новият модел.
* Входът за модела е валидиран чрез do-while цикъл и функцията is_string_empty.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, моделът й се заменя с нововъведения.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_model(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МОДЕЛ НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_model;
    do
    {
        cout << "Въведете нов модел на конфигурацията: ";
        getline(cin, new_model);
    } while (is_string_empty(new_model));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].model = new_model;
            cout << "\nМоделът е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_configuration_ram се използва за актуализиране на RAM стойността на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип double за RAM стойност.
* Входът за RAM стойността е валидиран чрез while цикъл (не може да е по-малка от 0).
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, RAM стойността й се заменя с нововъведената.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_ram(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА RAM СТОЙНОСТ НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова RAM стойност на конфигурацията: ";
    double new_ram;
    read_valid_double_value(new_ram);
    while (new_ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете нова RAM стойност на конфигурацията: ";
        read_valid_double_value(new_ram);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].ram = new_ram;
            cout << "\nRAM стойността е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_configuration_price се използва за актуализиране на цена на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип double за новата цена.
* Входът за цената е валидиран чрез while цикъл (не може да е по-малка от 0).
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, цената  й се заменя с нововъведената.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_price(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ЦЕНА НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова цена на конфигурацията: ";
    double new_price;
    read_valid_double_value(new_price);
    while (new_price <= 0)
    {
        cout << "Цената трябва да е по-голяма от 0.\nВъведете нова цена на конфигурацията: ";
        read_valid_double_value(new_price);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].price = new_price;
            cout << "\nЦената е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_configuration_status се използва за актуализиране на наличният статус на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип string за новият статус.
* Входът за статуса е валидиран чрез do-while цикъл.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, статусът й се заменя с нововъведения.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_configuration_status(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА НАЛИЧЕН СТАТУС НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    printf("\nВъведете нов наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
    string new_available_status;
    getline(cin, new_available_status);
    while (new_available_status != CONFIGURATION_IN_SELL_STATUS && new_available_status != CONFIGURATION_SOLD_STATUS)
    {
        printf("Наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
        getline(cin, new_available_status);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].available_status = new_available_status;
            cout << "\nНаличният статус е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_processor_manufacturer се използва за актуализиране на производителя
* на процесора на дадена конфигурация. Приема като параметри серийният номер на конфигурацията,
* масив с всички запазени конфигурации, брой запазени конфигурации.
* Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип string за новият производител.
* Входът за производителя е валидиран чрез do-while цикъл и is_string_empty.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, производителят на процесора й се заменя с нововъведения.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_processor_manufacturer(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ПРОИЗВОДИТЕЛ НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_manufacturer;
    do
    {
        cout << "Въведете нов производител на процесора: ";
        getline(cin, new_manufacturer);
    } while (is_string_empty(new_manufacturer));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.manufacturer = new_manufacturer;
            cout << "\nПроизводителят е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_processor_model се използва за актуализиране на модела на процесора
* на дадена конфигурация. Приема като параметри серийният номер на конфигурацията, масив
* с всички запазени конфигурации, брой запазени конфигурации. Извежда информативно съобщение
* и извежда детайли за конфигурацията, която предстои да бъде актуализирана чрез
* get_configuration_by_id и print_configuration. След това се дефинира променлива от тип string за новият модел.
* Входът за производителя е валидиран чрез do-while цикъл и is_string_empty.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, моделът на процесора й се заменя с нововъведения.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_processor_model(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МОДЕЛ НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_model;
    do
    {
        cout << "Въведете нов модел на процесора: ";
        getline(cin, new_model);
    } while (is_string_empty(new_model));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.model = new_model;
            cout << "\nМоделът е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_ processor_frequency се използва за актуализиране на честотата
* на процесора на дадена конфигурация. Приема като параметри серийният номер на конфигурацията,
* масив с всички запазени конфигурации, брой запазени конфигурации.
* Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип double за новата честота.
* Входът за честотата е валидиран чрез while цикъл (не може да е по-малка от 0).
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, честотата на процесора й се заменя с нововъведената.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_processor_frequency(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ЧЕСТОТА НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова честота на процесора: ";
    double new_frequency;
    read_valid_double_value(new_frequency);
    while (new_frequency <= 0)
    {
        cout << "Честотата трябва да е по-голяма от 0.\nВъведете нова честота на процесора: ";
        read_valid_double_value(new_frequency);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.frequency = new_frequency;
            cout << "\nЧестотата е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функцията update_ processor_cores се използва за актуализиране на броят ядра на процесора на дадена конфигурация.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение и извежда детайли за конфигурацията,
* която предстои да бъде актуализирана чрез get_configuration_by_id и print_configuration.
* След това се дефинира променлива от тип int за новият брой ядра.
* Входът за броят ядра е валидиран чрез while цикъл (не може да е по-малък от 0).
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, броят ядра на процесора й се заменят с нововъведените.
* Извежда се съобщение на екрана и обновената конфигурация.
*/
void update_processor_cores(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА БРОЙ ЯДРА НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нов брой ядра на процесора: ";
    int new_cores;
    read_valid_integer_value(new_cores);
    while (new_cores <= 0)
    {
        cout << "Броят ядра трябва да е по-голям от 0.\nВъведете нов брой ядра на процесора: ";
        read_valid_integer_value(new_cores);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.cores = new_cores;
            cout << "\nБроят ядра е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

/*
* Функционалността за актуализиране на елемент на процесора е реализирана във функцията update_processor.
* Приема като параметри серийният номер на конфигурацията, масив с всички запазени конфигурации,
* брой запазени конфигурации. Извежда информативно съобщение на екрана и визуализира подменю за редакция
* на елементи по процесора. Дефинира променлива от тип int, която служи за определяне на меню опцията.
* Меню опцията е валидирана чрез do-while цикъл.
* Чрез конструкцията switch се определя коя фунцкия за редакция да се задейства.
*/
void update_processor(string& configuration_id, Computer configurations[], int present_configurations_count)
{
    cout << "===== АКТУАЛИЗИРАНЕ НА ПРОЦЕСОР =====\n";

    int update_option;
    do
    {
        printf("\nВъведете %d, за да промените производителя на процесора.\nВъведете %d, за да промeните модела на процесора.\nВъведете %d, за да промените честотата на процесора.\nВъведете %d, за да промените броят ядра на процесора.\nВъведете %d, за да прекратите корекцията на данни на процесора.\n",
            UPDATE_PROCESSOR_MANUFACTURER_CHOICE, UPDATE_PROCESSOR_MODEL_CHOICE, UPDATE_PROCESSOR_FREQUENCY_CHOICE, UPDATE_PROCESSOR_CORES_COUNT_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", UPDATE_PROCESSOR_MANUFACTURER_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
            read_valid_integer_value(update_option);
        } while (update_option < UPDATE_PROCESSOR_MANUFACTURER_CHOICE || update_option > EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);

        system("cls");
        switch (update_option)
        {
        case UPDATE_PROCESSOR_MANUFACTURER_CHOICE:
            update_processor_manufacturer(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_MODEL_CHOICE:
            update_processor_model(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_FREQUENCY_CHOICE:
            update_processor_frequency(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_CORES_COUNT_CHOICE:
            update_processor_cores(configuration_id, configurations, present_configurations_count);
            break;
        }
    } while (update_option != EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
}

/*
* Функционалността за актуализиране на елемент на конфигурация е реализирана във функцията update_configuration.
* Приема като параметри масив с всички запазени конфигурации, брой запазени конфигурации.
* Извежда информативно съобщение на екрана и приканва потребителят да въведе сериен номер
* на конфигурация за редактиране. Входът е валидиран чрез do-while цикъл и is_string_empty.
* Ако конфигурация със въведения сериен номер не съществува се извежда съобщение и фунцкията
* прекратява своето изпълнение. Ако конфигурацията е намерана, но не е налична за продажба се извежда
* съобщение и функцията прекратява изпълнението си. При успешно намерена и налична конфигурация се извежда
* съобщение и се визуализира подменю за редакция на елементи по конфигурацията. Дефинира се променлива от тип int,
* която служи за определяне на меню опцията. Меню опцията е валидирана чрез do-while цикъл. Чрез конструкцията switch
* се определя коя фунцкия за редакция да се извика.
*/
void update_configuration(Computer configurations[], int present_configurations_count)
{
    cout << "===== АКТУАЛИЗИРАНЕ НА КОНФИГУРАЦИЯ ======\n\n";
    
    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации.\n";
        return;
    }
    
    string id;
    do
    {
        cout << "Въведете сериен номер на конфигурацията за актуализиране: ";
        getline(cin, id);
    } while (is_string_empty(id));

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!\n";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията е продадена и не може да бъде редактирана!\n";
        return;
    }

    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n\nДетайли за конфигурация:\n", id.c_str());
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    int update_option;
    do
    {
        printf("\nВъведете %d, за да промените сериен номер.\nВъведете %d, за да проемините марката.\nВъведете %d, за да промените модела.\nВъведете %d, за да промените RAM стойността.\nВъведете %d, за да промените цената.\nВъведете %d, за да промемите наличния статус.\nВъведете %d, за да направите промяна по данните на процесора.\nВъведете %d, за да прекратите корекцията на данни.\n",
            UPDATE_CONFIGURATION_ID_CHOICE, UPDATE_CONFIGURATION_BRAND_CHOICE, UPDATE_CONFIGURATION_MODEL_CHOICE, UPDATE_CONFIGURATION_RAM_CHOICE, UPDATE_CONFIGURATION_PRICE_CHOICE, UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE, UPDATE_CONFIGURATION_PROCESSOR_CHOICE, EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", UPDATE_CONFIGURATION_ID_CHOICE, EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
            read_valid_integer_value(update_option);
        } while (update_option < UPDATE_CONFIGURATION_ID_CHOICE || update_option > EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);

        system("cls");
        switch (update_option)
        {
        case UPDATE_CONFIGURATION_ID_CHOICE:
            update_configuration_id(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_BRAND_CHOICE:
            update_configuration_brand(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_MODEL_CHOICE:
            update_configuration_model(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_RAM_CHOICE:
            update_configuration_ram(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_PRICE_CHOICE:
            update_configuration_price(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE:
            update_configuration_status(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_PROCESSOR_CHOICE:
            update_processor(id, configurations, present_configurations_count);
            break;
        }
    } while (update_option != EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
}

/*
* Функцията change_configuration_availability_status_to_sold се използва за отбелязване
* на дадена конфигурация като продадена. Приема като параметри серийният номер на конфигурацията,
* масив с всички запазени конфигурации, брой запазени конфигурации.
* Обхождат се всички конфигурации и се проверява за съвпадение в серийният номер.
* След като се намери конфигурацията, наличният й статус се променя на „продадена“.
*/
void change_configuration_availability_status_to_sold(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].available_status = CONFIGURATION_SOLD_STATUS;
            return;
        }
    }
}

/*
* Функцията sell_configuration се използва за осъществяване на продажба по сериен номер.
* Приема като параметри масив с всички запазени конфигурации и брой запазени конфигурации.
* Извежда информативно съобщение и приканва потребителя да въведе сериен номер на конфигурацията за продажба.
* Входът е валидиран с do-while цикъл и is_string_empty. Ако конфигурация с този сериен номер не е намерена
* или намерената конфигурация е продадена, на екрана се извежда съобщение и функцията прекратява изпълнението си.
* Ако конфигуацията е намерена успешно и е налична се извеждат детайли за нея чрез функцията print_configuration.
* След това потребителят трябва да въведе цена, с която ще бъде закупена конфигурацията.
* Входът за цената е валидиран с while цикъл.
* Ако въведената цена не е достатъчна за покупка на екрана се извежда съобщение и функцията прекратява изпълнението си.
* В противен случай, наличният статус на конфигурацията се променя и се извежда ресто.
*/
void sell_configuration_by_id(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ ПО СЕРИЕН НОМЕР =====\n\n";

    string id;
    do
    {
        cout << "Въведете сериен номер на компютъра: ";
        getline(cin, id);
    } while (is_string_empty(id));


    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!\n";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията не е налична за продажба!\n";
        return;
    }

    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n\nДетайли за конфигурация:\n", id.c_str());
    print_configuration(computer);

    cout << "\nВъведете цена за покупка: ";
    double price_to_pay;
    read_valid_double_value(price_to_pay);
    while (price_to_pay <= 0)
    {
        cout << "Цената за покупка трябва да е по-голяма от 0.\nВъведете цена за покупка: ";
        read_valid_double_value(price_to_pay);
    }

    if (price_to_pay < computer.price)
    {
        printf("\nНедостатъчна сума пари!\nНеобходима сума: %.2f лв.\n", computer.price - price_to_pay);
        return;
    }

    change_configuration_availability_status_to_sold(id, configurations, present_configurations_count);
    double money_change = price_to_pay - computer.price;
    printf("\nКонфигурацията е успешно продадена!\nРесто: %.2f лв.\n", money_change);
}

/*
* Функцията read_processor_selling_data се използва за прочитане на желаните характеристики на процесора
* при осъществяване на продажба. Приема като входни параметри променливи по адрес от тип string,
* в които ще бъдат записани желаните характеристики на процесора.
*/
void read_processor_selling_data(string& manufacturer, string& model, string& frequency, string& cores)
{
    cout << "Въведете желан производител на процесора: ";
    getline(cin, manufacturer);
    cout << "Въведете желан модел на процесора: ";
    getline(cin, model);
    cout << "Въведете желана минимална честота на процесора: ";
    getline(cin, frequency);
    cout << "Въведете желан минимален брой ядра на процесора: ";
    getline(cin, cores);
}

/*
* Функцията read_computer_selling_data се използва за прочитане на желаните характеристики
* на компютъра при осъществяване на продажба.
* Приема като входни параметри променливи по адрес от тип string,
* в които ще бъдат записани желаните характеристики на компютъра.
*/
void read_computer_selling_data(string& brand, string& model, string& ram, string& price)
{
    cout << "Въведете желана марка на компютъра: ";
    getline(cin, brand);
    cout << "Въведете желан модел на компютъра: ";
    getline(cin, model);
    cout << "Въведете желана минимална RAM на компютъра: ";
    getline(cin, ram);
    cout << "Въведете максимална цена на компютъра: ";
    getline(cin, price);
}

/*
* Функцията find_computers_with_requirements се използва за намиране на конфигурации по посочени характеристики. 
* Обхожда всички запазени конфигурации като при всяка итерация стойностите на променливите selected_features и
* actual_featires са 0. Проверява се всяка една подадена характеристика дали е различна от празен string.
* Ако не е празен string, тогава потребителят иска конфигурацията да разполага с въведената характеристика.
* Стойността на selected_features нараства с 1. След като се открие поле, което не е празен string се проверява
* дали конфигурацията разполага с дадената характеристика. String стойностите се сравняват без значение от малка и
* главна буква. Ако конфигурацията разполага с желаната характеристика броячът actual_features също се увеличава с 1.
* Ако стойностите на променливите selected_features и actual_features са равни, конфигурацията отговаря на изискванията
* и се добавя към масива с намерените конфигурации.
*/
void find_computers_with_requirements(Computer configurations[], int present_configurations_count, Computer found_configurations[],
    int& found_configurations_count, string& processor_manufacturer, string& processor_model, string& processor_frequency,
    string& processor_cores, string& computer_brand, string& computer_model, string& computer_ram, string& computer_price,
    int& selected_features, int& actual_features)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        selected_features = 0;
        actual_features = 0;
        Computer computer = configurations[i];

        if (!is_string_empty(processor_manufacturer))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.manufacturer, processor_manufacturer)) actual_features++;
        }

        if (!is_string_empty(processor_model))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.model, processor_model)) actual_features++;
        }

        if (!is_string_empty(processor_frequency))
        {
            selected_features++;
            if (computer.processor.frequency >= stod(processor_frequency)) actual_features++;
        }

        if (!is_string_empty(processor_cores))
        {
            selected_features++;
            if (computer.processor.cores >= stoi(processor_cores)) actual_features++;
        }

        if (!is_string_empty(computer_brand))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.brand, computer_brand)) actual_features++;
        }

        if (!is_string_empty(computer_model))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.model, computer_model)) actual_features++;
        }

        if (!is_string_empty(computer_ram))
        {
            selected_features++;
            if (computer.ram >= stod(computer_ram)) actual_features++;
        }

        if (!is_string_empty(computer_price))
        {
            selected_features++;
            if (computer.price <= stod(computer_price)) actual_features++;
        }

        if (selected_features == actual_features) found_configurations[found_configurations_count++] = computer;
    }
}

/*
* Функцията sell_configuration_by_requirements се използва за осъществяване на продажба по желани характеристики.
* Декларира string променливи за всяка една характеристика на конфигурацията. След това се прочитат стойности
* за променливите чрез функциите read_processor_selling_data и read_computer_selling_data.
* След като стойностите са прочетени се дефинира масив, в който да бъдат запазени конфигурациите,
* отговарящи на изискванията, брояч за намерените конфигурации и две int променливи, които следят
* броя на желаните характеристики и броя на характеристиките, които конфигурацията покрива.
* След това се извиква фунцкията find_computers_with_requirements с необходимите параметри.
* Ако броячът за намерените конфигурации е 0, функцията извежда съобщение на конзолата и
* прекратява изпълнението си. Намерените конфигурации се извеждат на екрана чрез фунцкията
* print_all_configurations. След това се изчаква вход от потребителя дали да бъде осъществена продажба.
* Ако потребителят потвърди, осъществяването на продажбата става с фунцкията sell_configuration_by_id.
*/
void sell_configuration_by_requirements(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ ПО ХАРАКТЕРИСТИКИ =====\n\n* Ако желаете да пропуснете характерстика, оставете съответното поле празно.\n\n";

    string processor_manufacturer, processor_model, processor_frequency, processor_cores, computer_brand, computer_model, computer_ram, computer_price;
    read_processor_selling_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
    read_computer_selling_data(computer_brand, computer_model, computer_ram, computer_price);

    Computer found_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    int found_configurations_count = 0;
    int selected_features, actual_features;

    find_computers_with_requirements(configurations, present_configurations_count, found_configurations,
        found_configurations_count, processor_manufacturer, processor_model, processor_frequency,
        processor_cores, computer_brand, computer_model, computer_ram, computer_price, selected_features, actual_features);

    if (found_configurations_count == 0)
    {
        cout << "\nНе са намерени конфигурации отговарящи на посочените изисквания.\n";
        return;
    }

    cout << "\nПодходящи конфигурации:\n";
    print_all_configurations(found_configurations, found_configurations_count);

    cout << "\nЖелаете ли да осъществите продажба? (Y/N): ";
    char confirm;
    cin >> confirm;
    while (tolower(confirm) != 'y' && tolower(confirm) != 'n')
    {
        cout << "Въведете Y, за да влезете в режим на продажба.\nВъведете N, за да откажете осъществяването на продажба.\nВъведете избор (Y/N): ";
        cin >> confirm;
    }

    if (tolower(confirm) == 'y')
    {
        cout << endl;
        cin.clear();
        cin.ignore();
        sell_configuration_by_id(configurations, present_configurations_count);
    }
    else cout << "\nПродажба по въведени харакетеристики бе отказана.\n";
}

/*
* Функционалността за осъществяване на продажба е реализирана във функцията sell_configuration.
* Приема като параметри масив с всички запазени конфигурации и брой запазени конфигурации.
* Извежда се информативно съобщение на екрана. Ако няма запазени конфигурации се извежда съобщение
* и функцията приключва изпълнението си. Декларира се int променлива за меню опция.
* Чрез switch констрикцията и подменю се определя коя фунцкия за продажба да се използва
* - sell_configuration_by_id или sell_configuration_by_requirements.
*/
void sell_configuration(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    int menu_choice;
    do
    {
        printf("\nВъведете %d, за да осъществите продажба по сериен номер.\nВъведете %d, за да осъществите продажба по определени характеристики.\nВъведете %d, за да напуснете продажното меню.\n",
            SELL_BY_SERIAL_NUMBER_CHOICE, SELL_BY_REQUIREMENTS_CHOICE, EXIT_FROM_SELLING_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", SELL_BY_SERIAL_NUMBER_CHOICE, EXIT_FROM_SELLING_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < SELL_BY_SERIAL_NUMBER_CHOICE || menu_choice > EXIT_FROM_SELLING_MENU_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case SELL_BY_SERIAL_NUMBER_CHOICE:
            sell_configuration_by_id(configurations, present_configurations_count);
            break;

        case SELL_BY_REQUIREMENTS_CHOICE:
            sell_configuration_by_requirements(configurations, present_configurations_count);
            break;
        }
    } while (menu_choice != EXIT_FROM_SELLING_MENU_CHOICE);
}

/*
* Функцията search_for_configurations_by_availability_status проверява за съществуваща конфигурация
* по даден наличен статус. Връща като резултат булева стойност. Приема като параметри масива,
* в който са записани всички конфигурации, броя на записани конфигурации и наличният статус за проверка.
* Обхождат се всички конфигурации и се проверява дали текущата конфигурация има посочения наличе статус.
* Ако условието е изпълнено функцията връща true, в противен случай – false.
*/
bool search_for_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].available_status == available_status) return true;
    }
    return false;
}

/*
* Сравняване на две числа и връщане на по-малкото.
*/
int find_min_number(int first, int second)
{
    if (first < second) return first;
    return second;
}

/*
* Функцията sort_configurations_by_id сортира запазените конфигурации по сериен номер в нов масив.
* Приема като параметри масива, в който са записани всички конфигурации, броя на записани конфигурации
* и нов масив, в който се запазват сортирани конфигурациите. Обхождат се всички конфигурации
* в първия масив и се копират във врори масив (този са сортираните конфигурации).
* Конфигурациите се сортират във възходящ ред чрез метода на мехурчето.
* Алгоритъмът е оптимизиран с булева променлива, която проверява дали се е осъществила размяна на елементи.
* При сортирането по сериен номер се сравняват ASCII стойностите на символите в серийните номера.
* Обхожда се по-краткият сериен номер чрез функцията find_min_number.
*/
void sort_configurations_by_id(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            Computer first = sorted_configurations[j];
            Computer second = sorted_configurations[j + 1];
            int min_id_length = find_min_number(first.id.length(), second.id.length());

            for (int k = 0; k < min_id_length; k++)
            {
                if (first.id[k] > second.id[k])
                {
                    Computer temp = sorted_configurations[j];
                    sorted_configurations[j] = sorted_configurations[j + 1];
                    sorted_configurations[j + 1] = temp;
                    swapped = true;
                    break;
                }
            }
        }
        if (!swapped) break;
    }
}

/*
* Функцията print_configurations_by_availability_status извежда всички конфигурации с даден наличен статус.
* Приема като параметри масива, в който са записани всички конфигурации, броя на записани конфигурации и наличният
* статус за проверка. Обхождат се всички конфигурации и се проверява дали текущата конфигурация има посочения наличеn статус.
* Ако условието е изпълнено конфигурацията се извежда чрез print_configuration.
*/
void print_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].available_status == available_status) print_configuration(configurations[i]);
    }
}

/*
* Функционалността за извеждането на наличните конфигурации сортирани по сериен номер е
* реализирана във функцията print_available_configurations_sorted_by_id.
* Приема като параметри масива, в който са записани всички конфигурации и броя на записани конфигурации.
* Извежда информативно съобщение екрана. Ако няма запазени конфигурации или няма налични такива се извежда
* съответното съобщение на екрана и функцията прекратява своето изпълнение. Ако съществуват налични конфигурации,
* те се сортират в декларирания масив и се извеждат на екрана чрез sort_configurations_by_id и
* print_configurations_by_availability_status.
*/
void print_available_configurations_sorted_by_id(Computer configurations[], int present_configurations_count)
{
    cout << "===== НАЛИЧНИ КОНФИГУРАЦИИ, СОРТИРАНИ ПО СЕРИЕН НОМЕР =====\n";
    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    if (!search_for_configurations_by_availability_status(configurations, present_configurations_count, CONFIGURATION_IN_SELL_STATUS))
    {
        cout << "\nНяма налични конфигурации.\n";
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_id(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_availability_status(sorted_configurations, present_configurations_count, CONFIGURATION_IN_SELL_STATUS);
}

/*
* Функцията configuration_exists_by_processor_model_and_ram проверява за съществуваща конфигурация
* по модел на процесора и RAM стойност. Връща като резултат булева стойност.
* Приема като параметри масива, в който са записани всички конфигурации,
* броя на записани конфигурации, моделът на процесора и RAM стойността за проверка.
* Обхождат се всички конфигурации и се проверява дали текущата конфигурация има посочения модел процесор и RAM.
* Ако условието е изпълнено функцията връща true, в противен случай – false.
*/
bool configuration_exists_by_processor_model_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (compare_strings_case_insensitive(configurations[i].processor.model, processor_model) && configurations[i].ram == ram) return true;
    }
    return false;
}

/*
* Функцията sort_configurations_by_price_desc сортира запазените конфигурации по цена в низходящ ред в нов масив.
* Приема като параметри масива, в който са записани всички конфигурации, броя на записани конфигурации и нов масив,
* в който се запазват сортирани конфигурациите.
* Обхождат се всички конфигурации в първия масив и се копират във втория масив
* (този за сортираните конфигурации).
* Конфигурациите се сортират във низходящ ред чрез метода на мехурчето. Алгоритъмът е оптимизиран с булева променлива,
* която проверява дали се е осъществила размяна на елементи.
*/
void sort_configurations_by_price_desc(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            if (sorted_configurations[j].price < sorted_configurations[j + 1].price)
            {
                Computer temp = sorted_configurations[j];
                sorted_configurations[j] = sorted_configurations[j + 1];
                sorted_configurations[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

/*
* Функцията print_configurations_by_brand_and_ram извежда всички конфигурации с даден модел на процесора и RAM памет.
* Приема като параметри масива, в който са записани всички конфигурации, броя на записани конфигурации,
* моделът на процесора и RAM стойността за проверка. Обхождат се всички конфигурации
* и се проверява дали текущата конфигурация има посочените параметри.
* Ако условието е изпълнено конфигурацията се извежда чрез print_configuration.
*/
void print_configurations_by_brand_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (compare_strings_case_insensitive(configurations[i].processor.model, processor_model) && configurations[i].ram == ram) print_configuration(configurations[i]);
    }
}

/*
* Функционалността за извеждането на конфигурации с даден модел на процесора и RAM памет сортирани по цена
* в низходящ ред е реализирана във функцията print_configurations_by_brand_and_ram_sorted_by_price_desc.
* Приема като параметри масива, в който са записани всички конфигурации и броя на записани конфигурации.
* Извежда информативно съобщение екрана. Прочита стойности за желаният модел процесор и RAM памет.
* Двата входа са валидирани. Ако няма запазени конфигурации или няма конфигурации с желаният модел
* процесор и RAM памет се извежда съответното съобщение на екрана и функцията прекратява своето изпълнение.
* Ако съществуват конфигурации, те се сортират в декларирания масив и се извеждат на екрана
* чрез sort_configurations_by_price_desc и print_configurations_by_availability_status.
*/
void print_configurations_by_brand_and_ram_sorted_by_price_desc(Computer configurations[], int present_configurations_count)
{
    cout << "===== КОНФИГУРАЦИИ С ДАДЕН МОДЕЛ ПРОЦЕСОР И RAM ПАМЕТ, СОРТИРАНИ ОТ НАЙ-СКЪПАТА КЪМ НАЙ-ЕВТИНАТА =====\n\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    string processor_model;
    do
    {
        cout << "Въведете желан модел процесор: ";
        getline(cin, processor_model);
    } while (is_string_empty(processor_model));

    cout << "Въведете RAM на компютъра (GB): ";
    double ram;
    read_valid_double_value(ram);
    while (ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете RAM на компютъра (GB): ";
        read_valid_double_value(ram);
    }

    if (!configuration_exists_by_processor_model_and_ram(configurations, present_configurations_count, processor_model, ram))
    {
        printf("\nНе са намерени конфигурации с модел на процесора %s и RAM %.2f GB.\n", processor_model.c_str(), ram);
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_price_desc(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_brand_and_ram(sorted_configurations, present_configurations_count, processor_model, ram);
}

/*
* Функцията sort_configurations_by_processor_model сортира запазените конфигурации
* по модел прецесор в нов масив. Приема като параметри масива, в който са записани всички конфигурации,
* броя на записани конфигурации и нов масив, в който се запазват сортирани конфигурациите.
* Обхождат се всички конфигурации в първия масив и се копират във втория масив
* (този за сортираните конфигурации). Конфигурациите се сортират във възходящ ред чрез метода на мехурчето.
* Алгоритъмът е оптимизиран с булева променлива, която проверява дали се е осъществила размяна на елементи.
* При сортирането по модел на процесора се сравняват ASCII стойностите на символите в модела на процесора.
* Обхожда се по-краткият сериен номер чрез функцията find_min_number.
*/
void sort_configurations_by_processor_model(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            Processor first = sorted_configurations[j].processor;
            Processor second = sorted_configurations[j + 1].processor;
            int min_model_length = find_min_number(first.model.length(), second.model.length());

            for (int k = 0; k < min_model_length; k++)
            {
                if (first.model[k] > second.model[k])
                {
                    Computer temp = sorted_configurations[j];
                    sorted_configurations[j] = sorted_configurations[j + 1];
                    sorted_configurations[j + 1] = temp;
                    swapped = true;
                    break;
                }
            }
        }
        if (!swapped) break;
    }
}

/*
* Функционалността за извеждането на продадените конфигурации сортирани по модел на процесора
* е реализирана във функцията print_unavailable_configurations_sorted_by_processor_model.
* Приема като параметри масива, в който са записани всички конфигурации и броя на записани конфигурации.
* Извежда информативно съобщение екрана. Ако няма запазени конфигурации или няма продадени такива
* се извежда съответното съобщение на екрана и функцията прекратява своето изпълнение.
* Ако съществуват продадени конфигурации, те се сортират в декларирания масив и се извеждат
* на екрана чрез sort_configurations_by_processor_model и print_configurations_by_availability_status.
*/
void print_unavailable_configurations_sorted_by_processor_model(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАДЕНИ КОНФИГУРАЦИИ, СОРТИРАНИ ПО МОДЕЛ НА ПРОЦЕСОРА =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    if (!search_for_configurations_by_availability_status(configurations, present_configurations_count, CONFIGURATION_SOLD_STATUS))
    {
        cout << "\nНяма продадени конфигурации.\n";
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_processor_model(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_availability_status(sorted_configurations, present_configurations_count, CONFIGURATION_SOLD_STATUS);
}

/*
* Функционалността за осъществяване на одит е реализирана във функцията make_configurations_audit.
* Приема като параметри масива, в който са записани всички конфигурации и броя на записани конфигурации.
* Извежда информативно съобщение екрана. Ако няма запазени конфигурации се извежда съответното съобщение
* на екрана и функцията прекратява своето изпълнение. Дефинира се променлива за меню опция
* и чрез switch констрикция и подменю се определя какъв одит да бъде направен.
*/
void make_configurations_audit(Computer configurations[], int present_configurations_count)
{
    cout << "===== ОДИТ НА КОНФИГУРАЦИИ =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    int menu_choice;
    do
    {
        printf("\nВъведете %d, за да изведете всички конфигурации, които са в продажба, сортирани по сериен номер.\nВъведете %d, за да изведете всички конфигурации с даден модел процесор и RAM памет, сортирани по цена от най-скъпия към най-евтиния.\nВъведете %d, за да изведете продадените конфигурации, сортирани по модел на процесора.\nВъведете %d, за да напуснете одитното меню.\n",
            PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE, PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE, PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE, EXIT_FROM_AUDIT_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE, EXIT_FROM_AUDIT_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE || menu_choice > EXIT_FROM_AUDIT_MENU_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE:
            print_available_configurations_sorted_by_id(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE:
            print_configurations_by_brand_and_ram_sorted_by_price_desc(configurations, present_configurations_count);
            break;

        case PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE:
            print_unavailable_configurations_sorted_by_processor_model(configurations, present_configurations_count);
            break;
        }
    } while (menu_choice != EXIT_FROM_AUDIT_MENU_CHOICE);
}

/*
* Функцията store_configurations_in_file записва съществуващите конфигурации във файл configurations.dat.
* Приема като параметри масива за записване на конфигурациите и броят на запазените конфиграции.
* Дефинира променлива от тип fstream и отваря файл с име configuratios.dat.
* Ако няма запазени конфигурации или файлът не се отвори успешно се извежда
* съобщение за грешка и фунцкията прекратява изпъленинието си.
* При успешно отваряне на файла, информацията за всички конфигурации се запазва в него.
*/
void store_configurations_in_file(Computer configurations[], int present_configurations_count)
{
    if (present_configurations_count == 0)
    {
        cout << "Няма конфигурации за записване във файл.\n";
        return;
    }
    fstream configurations_data;
    configurations_data.open("configurations.dat", ios::out);

    if (configurations_data.fail())
    {
        cout << "Грешка в отварянето на файла.";
        return;
    }

    configurations_data.setf(ios::fixed);
    configurations_data.setf(ios::showpoint);
    configurations_data.precision(DIGITS_TO_PRINT_AFTER_DECIMAL_POINT);
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        Processor processor = computer.processor;

        configurations_data
            << "Сериен номер: " << computer.id.c_str()
            << "\nМарка: " << computer.brand.c_str()
            << "\nМодел: " << computer.model.c_str()
            << "\nRAM памет: " << computer.ram << " GB"
            << "\nПроцесор: \n\tПроизводител: " << processor.manufacturer
            << "\n\tМодел: " << processor.model
            << "\n\tТактова честота: " << processor.frequency << " GHz"
            << "\n\tБрой ядра: " << processor.cores
            << "\nЦена: " << computer.price << " лв."
            << "\nНаличен статус: " << computer.available_status << "\n";

        if (i != present_configurations_count - 1) configurations_data << "\n";
    }
    configurations_data.close();
    printf("%d конфигурации бяха записани във файл.\n", present_configurations_count);
}
