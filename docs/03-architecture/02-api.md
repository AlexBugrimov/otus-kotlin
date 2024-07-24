# API

# Описание сущности utility bill (квитанция/счет за коммунальные услуги)
1. UtilityBillId - Идентификатор модели
2. MeterReadings - Показания счетчиков
3. TotalAmount - Общая сумма
4. Period - Период
5. CreatedAt - Дата время создания
6. UpdatedAt - Дата время обновления

# Описание сущности Meter reading (показания счетчика)
1. MeterReadingId - Идентификатор модели
2. Name - Наименование счетчика 
3. Value - Показание счетчика
4. Tariff - Тариф на услугу
5. VolumeForPeriod - Объем за период
6. AccruedSum - Начисленная сумма
7. PaidAmount - Оплаченная сумма
8. Image - Изображение

# Описание сущности tariff (тариф)
1. TariffId - Идентификатор модели
2. Name - Наименование тарифа
3. Value - Значение
4. ValidityPeriod - Период действия 