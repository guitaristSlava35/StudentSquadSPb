package com.example.studentsquadspb.android.data

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.data.model.info.MemberInfo
import com.example.studentsquadspb.android.data.model.MemberSeason
import com.example.studentsquadspb.android.data.model.MemberSquad
import com.example.studentsquadspb.android.data.model.position.PositionCategory
import com.example.studentsquadspb.android.data.model.position.Position
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.SquadMemberType
import com.example.studentsquadspb.android.data.model.position.ExPosition
import com.example.studentsquadspb.android.data.model.position.ExPositionCategory
import com.example.studentsquadspb.android.data.model.position.MemberExPosition
import com.example.studentsquadspb.android.data.model.position.ProfileExPosition
import javax.inject.Inject
import javax.inject.Singleton

interface SquadRepository{
    fun getSquads(): List<SquadInfo>
    fun getSquad(id: Int): SquadInfo?
    fun getMembers(squadId: Int): List<SquadMember>
    fun getMember(id: Int): MemberInfo?
    fun getMemberSquads(memberId: Int): List<MemberSquad>
    fun getFighterSquads(memberId: Int): List<SquadInfo>
    fun getProfileDirectionWork(memberId: Int): List<String>
    fun getProfileHadquarters(memberId: Int): List<String>
    fun getProfileRoleSquad(memberId: Int): List<Position>
    fun getMemberSeasons(memberId: Int): List<Int>
    fun getProfileExRoleSquad(memberId: Int): List<ProfileExPosition>
}

@Singleton
class SquadRepositoryImpl @Inject constructor() : SquadRepository {

    private val positions = listOf(
        Position(1, "Командир", PositionCategory.COMMAND_SQUAD),
        Position(2, "Комиссар", PositionCategory.COMMAND_SQUAD),
        Position(3, "Мастер", PositionCategory.COMMAND_SQUAD),
        Position(4, "Комендант", PositionCategory.COMMAND_SQUAD),
        Position(5, "Методист", PositionCategory.COMMAND_SQUAD),
        Position(6, "Руководитель Пресс-центра", PositionCategory.COMMAND_SQUAD),
        Position(7, "Член Пресс-центра"),
        Position(8, "Флагоносец", PositionCategory.FLAG),
        Position(9, "Лучший боец"),
    )

    private val exPositions = listOf(
        ExPosition(1, "Экс-Командир", ExPositionCategory.EX_COMMAND_SQUAD),
        ExPosition(2, "Экс-Комиссар", ExPositionCategory.EX_COMMAND_SQUAD),
        ExPosition(3, "Экс-Мастер", ExPositionCategory.EX_COMMAND_SQUAD),
        ExPosition(4, "Экс-Комендант", ExPositionCategory.EX_COMMAND_SQUAD),
        ExPosition(5, "Экс-Методист", ExPositionCategory.EX_COMMAND_SQUAD),
        ExPosition(6, "Экс-Флагоносец", ExPositionCategory.EX_FLAG),

    )

    private val squads = listOf(
        SquadInfo(1, R.drawable.ic_altair, "Альтаир", "01.01.1999","ССервО", "СПбПУ"),
        SquadInfo(2, R.drawable.ic_artifex, "Артифекс", "01.01.1999","САО", "СПбПУ"),
        SquadInfo(3, R.drawable.ic_django, "Джанго", "01.01.1999","ССхО", "СПбПУ"),
        SquadInfo(4, R.drawable.ic_legenda, "Легенда", "01.01.1999","СПО", "СПбПУ"),
        SquadInfo(5, R.drawable.ic_los, "Лось", "01.01.1999","ССхО", "СПбПУ"),
        SquadInfo(6, R.drawable.ic_plamya, "Пламя", "01.01.1999","ССО", "СПбПУ"),
        SquadInfo(7, R.drawable.ic_provorniy, "Проворный", "01.01.1999","СОП", "СПбПУ"),
        SquadInfo(8, R.drawable.ic_svarog, "Сварог", "01.01.1999","СПрО", "СПбПУ"),
        SquadInfo(9, R.drawable.ic_orion, "Орион", "01.01.1999","ССО", "СПбПУ")
    )

    private val members = listOf(
        MemberInfo(id = 1, photo = R.drawable.ic_logo_spbso, surname = "Киселёва", name = "Марина", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 2, photo = R.drawable.ic_logo_spbso, surname = "Павленко", name = "Полина", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 3, surname = "Гузов", name = "Глеб", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 4, photo = R.drawable.ic_logo_spbso, surname = "Ефимова", name = "Мария", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 5, photo = R.drawable.ic_logo_spbso, surname = "Коноплёва", name = "Виктория", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 6, photo = R.drawable.ic_logo_spbso, surname = "Кузнецов", name = "Вячеслав", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 7, surname = "Лисина", name = "Александра", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 8, photo = R.drawable.ic_logo_spbso, surname = "Бабкина", name = "Екатерина", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 9, surname = "Рандомный", name = "Чел", patronymic = "-", dateBirthday = "01.01.1999"),
        )

    private val links = listOf(
        MemberSquad(1, 1, "2025", SquadMemberType.COMMAND, listOf(1)),
        MemberSquad(2, 1, "2026", SquadMemberType.COMMAND, listOf(2)),
        MemberSquad(3, 1, "2025", SquadMemberType.COMMAND, listOf(3)),
        MemberSquad(4, 1, "2026", SquadMemberType.COMMAND, listOf(4)),
        MemberSquad(5, 1, "2025", SquadMemberType.COMMAND, listOf(6)),
        MemberSquad(6, 1, "2025", SquadMemberType.FIGHTER, listOf(1, 7, 8, 9)),
        MemberSquad(7, 1, "2024", SquadMemberType.FIGHTER, listOf(1, 7, 8, 9)),
        MemberSquad(8, 1, "2024", SquadMemberType.FIGHTER),
        MemberSquad(6, 7, "2027", SquadMemberType.FIGHTER),
        MemberSquad(9, 1, "2027", SquadMemberType.CANDIDATE),
    )

    private val seasons = listOf(
        MemberSeason(1, 2025),
        MemberSeason(1, 2026),
        MemberSeason(6, 2025),
        MemberSeason(6, 2026),
        MemberSeason(8, 2024),
        MemberSeason(8, 2025),
        MemberSeason(8, 2026),
    )

    private val exPositionMember = listOf(
        MemberExPosition(memberId = 6, yearSeason = 2026, exPosition = 6),
        MemberExPosition(memberId = 6, yearSeason = 2025, exPosition = 1),
        MemberExPosition(memberId = 6, yearSeason = 2024, exPosition = 2),
        MemberExPosition(memberId = 6, yearSeason = 2023, exPosition = 2),

    )

    override fun getSquads() = squads

    override fun getSquad(id: Int) = squads.find {it.id == id}

    override fun getMembers(SquadId: Int): List<SquadMember> =
        links.filter { it.idSquad == SquadId }.mapNotNull { link ->
            members.find { it.id == link.idMember }?.let { person ->
                SquadMember(
                    person.id,
                    person.photo,
                    listOf(person.name, person.surname)
                    .filter { it.isNotBlank() }
                    .joinToString(" "),
                    link.dateJoinig,
                    link.type,
                    positions.filter { it.id in link.positionIds }
                )
            }
        }

    override fun getMember(id: Int) = members.find {
        it.id == id
    }

    override fun getMemberSquads(memberId: Int) = links.filter {
        it.idMember == memberId
    }

    override fun getFighterSquads(memberId: Int): List<SquadInfo> = links
        .filter { it.idMember == memberId && (it.type.isFighter)  }
        .mapNotNull { link -> squads.find {it.id == link.idSquad} }
        .distinctBy { it.id }

    override fun getProfileHadquarters(memberId: Int): List<String>  = links
        .filter { it.idMember == memberId }
        .mapNotNull { link -> squads.find {it.id == link.idSquad} }
        .map {it.headquarters}
        .distinct()

    override fun getProfileDirectionWork(memberId: Int): List<String>  = links
        .filter { it.idMember == memberId }
        .mapNotNull { link -> squads.find {it.id == link.idSquad} }
        .map {it.directionWork}
        .distinct()

    override fun getProfileRoleSquad(memberId: Int): List<Position> = links
        .filter { it.idMember == memberId }
        .flatMap { link -> positions.filter { it.id in link.positionIds } }
        .distinctBy { it.id }

    override fun getMemberSeasons(memberId: Int): List<Int> = seasons
        .filter { it.memberId == memberId }
        .map{it.yearSeason}
        .distinct()
        .sorted()

    override fun getProfileExRoleSquad(memberId: Int): List<ProfileExPosition> = exPositionMember
        .filter { it.memberId == memberId}
        .mapNotNull { link ->
            exPositions.find {it.id == link.exPosition}
                ?.let { ProfileExPosition(link.yearSeason, it) }
        }
        .sortedBy { it.year }

}

@DrawableRes
fun directionLogo(direction: String): Int {
    return when(direction){
        "ССО" -> R.drawable.ic_logo_sso
        "СПО" -> R.drawable.ic_logo_spo
        "ССхО" -> R.drawable.ic_logo_ssho
        "ССервО" -> R.drawable.ic_logo_sservo
        "САО" -> R.drawable.ic_logo_sao
        "СОП" -> R.drawable.ic_logo_sop
        else -> R.drawable.ic_logo_sso
    }
}
fun headquartersLogo(headquarters: String): Int{
    return when(headquarters){
        "СПбПУ" -> R.drawable.ic_spbpu
        "Военмех" -> R.drawable.ic_bgtu
        "ГУАП" -> R.drawable.ic_guap
        "ИТМО" -> R.drawable.ic_itmo
        "ЛЭТИ" -> R.drawable.ic_leti
        "ПСПбГМУ" -> R.drawable.ic_pspbgmu
        "РГПУ" -> R.drawable.ic_rgpu
        "СПбГАСУ" -> R.drawable.ic_spbgasu
        "СПбГЭУ" -> R.drawable.ic_spbgeu
        "СПбГМТУ" -> R.drawable.ic_spbgmtu
        "СПбГУПТД" -> R.drawable.ic_spbguptd
        "СПбГУ" -> R.drawable.ic_spbgu
        "СПбГУТ" -> R.drawable.ic_spbgut
        "СЗГМУ" -> R.drawable.ic_szgmu
        "Технологический институт" -> R.drawable.ic_tu
        else -> R.drawable.ic_spbso
    }
}

fun positionLogo(positionId: Int): Int? = when (positionId){
    1 -> R.drawable.ic_comandir_1
    2 -> R.drawable.ic_comissar_1
    3 -> R.drawable.ic_master_1
    4 -> R.drawable.ic_master_1
    5 -> R.drawable.ic_metodist_1
    6 -> R.drawable.ic_rpc
    7 -> R.drawable.ic_press_center
    8 -> R.drawable.ic_flagonosec
    9 -> R.drawable.ic_best_fighter
    else -> null
}

fun exPositionLogo(expositionId: Int): Int? = when (expositionId){
    1 -> R.drawable.ic_ex_comandir_1
    2 -> R.drawable.ic_ex_commisar_1
    3 -> R.drawable.ic_master_1
    4 -> R.drawable.ic_master_1
    5 -> R.drawable.ic_ex_metodist_1
    6 -> R.drawable.ic_ex_flag
    else -> null
}