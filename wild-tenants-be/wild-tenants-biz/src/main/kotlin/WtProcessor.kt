package ru.bugrimov.wt.biz

import ru.bugrimov.wt.biz.general.initStatus
import ru.bugrimov.wt.biz.general.operation
import ru.bugrimov.wt.biz.general.stubs
import ru.bugrimov.wt.biz.repo.*
import ru.bugrimov.wt.biz.stubs.*
import ru.bugrimov.wt.biz.validation.*
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.common.models.WtCommand
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtUbLock
import ru.bugrimov.wt.lib.cor.chain
import ru.bugrimov.wt.lib.cor.rootChain
import ru.bugrimov.wt.lib.cor.worker

class WtProcessor(
    private val corSettings: WtCorSettings = WtCorSettings.NONE,
) {

    suspend fun exec(ctx: WtContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain {
        initStatus("Инициализация статуса")
        operation("Создание квитанции", WtCommand.CREATE) {
            stubs("Обработка заглушек") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadMeterReadings("Имитация ошибки валидации показаний счетчиков")
                stubValidationBadPeriod("Имитация ошибки валидации периода")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный stub недопустим")
            }
            validation {
                worker("Копируем поля в ubValidating") { ubValidating = request.deepCopy() }
                worker("Очистка id") { ubValidating.id = WtUbId.NONE }
                validateMeterReadingsNotEmpty("Проверка, что переданы не пустой список с показаниями счетчиков")
                validateMeterReadingsGreaterThanOrEqualToZero("Проверка, что переданные показания больше или равны 0")
                validatePeriod("Проверка периода")
                finishValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Получить квитанцию", WtCommand.READ) {
            stubs("Обработка заглушек") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный stub недопустим")
            }
            validation {
                worker("Копируем поля в ubValidating") { ubValidating = request.deepCopy() }
                worker("Очистка id") { ubValidating.id = WtUbId(ubValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата идентификатора")
                finishValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика чтения"
                repoRead("Чтение квитанции из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == WtState.RUNNING }
                    handle { ubRepoDone = ubRepoRead }
                }
            }
            prepareResult("Подготовка ответа")
        }
        operation("Изменить квитанцию", WtCommand.UPDATE) {
            stubs("Обработка заглушек") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadMeterReadings("Имитация ошибки валидации показаний счетчиков")
                stubValidationBadPeriod("Имитация ошибки валидации периода")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный stub недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { ubValidating = request.deepCopy() }
                worker("Очистка id") { ubValidating.id = WtUbId(ubValidating.id.asString().trim()) }
                worker("Очистка lock") { ubValidating.lock = WtUbLock(ubValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                validateMeterReadingsNotEmpty("Проверка, что переданы не пустой список с показаниями счетчиков")
                validateMeterReadingsGreaterThanOrEqualToZero("Проверка, что переданные показания больше или равны 0")
                validatePeriod("Проверка периода")
                finishValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика сохранения"
                repoRead("Чтение квитанции из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareUpdate("Подготовка квитанции для обновления")
                repoUpdate("Обновление квитанции в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Удалить квитанцию", WtCommand.DELETE) {
            stubs("Обработка заглушек") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный stub недопустим")
            }
            validation {
                worker("Копируем поля в ubValidating") { ubValidating = request.deepCopy() }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                finishValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика удаления"
                repoRead("Чтение квитанции из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление квитанции из БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Поиск квитанции", WtCommand.SEARCH) {
            stubs("Обработка заглушек") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный stub недопустим")
            }
            validation {
                worker("Копируем поля в ubFilterValidating") { ubFilterValidating = filterRequest.deepCopy() }
                validateSearchPeriod("Валидация периода поиска в фильтре")

                finishAdFilterValidation("Успешное завершение процедуры валидации")
            }
            repoSearch("Поиск квитанции в БД по фильтру")
            prepareResult("Подготовка ответа")
        }
    }.build()
}