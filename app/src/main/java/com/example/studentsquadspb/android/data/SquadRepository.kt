package com.example.studentsquadspb.android.data

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.data.model.MemberInfo
import com.example.studentsquadspb.android.data.model.MemberSquad
import com.example.studentsquadspb.android.data.model.Position
import com.example.studentsquadspb.android.data.model.SquadInfo
import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.SquadMemberType

interface SquadRepository{
    fun getSquads(): List<SquadInfo>
    fun getSquad(id: Int): SquadInfo?
    fun getMembers(squadId: Int): List<SquadMember>
}

object FakeSquadRepository : SquadRepository{

    private val positions = listOf(
        Position(1, "Командир"),
        Position(2, "Комиссар"),
        Position(3, "Мастер"),
        Position(4, "Комендант"),
        Position(5, "Методист"),
        Position(6, "Руководитель Пресс-центра"),
        Position(7, "Член Пресс-центра"),
        Position(8, "Флагоносец"),
        Position(9, "Лучший боец"),
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
        MemberInfo(id = 8, photo = R.drawable.ic_logo_spbso, name = "Бабкина", "Екатерина", patronymic = "-", dateBirthday = "01.01.1999"),
        MemberInfo(id = 9, surname = "Рандомный", name = "Чел", patronymic = "-", dateBirthday = "01.01.1999"),
        )

    private val links = listOf(
        MemberSquad(1, 1, "2025", SquadMemberType.COMMAND, listOf(1)),
        MemberSquad(2, 1, "2026", SquadMemberType.COMMAND, listOf(2)),
        MemberSquad(3, 1, "2025", SquadMemberType.COMMAND, listOf(3)),
        MemberSquad(4, 1, "2026", SquadMemberType.COMMAND, listOf(4)),
        MemberSquad(5, 1, "2025", SquadMemberType.COMMAND, listOf(6)),
        MemberSquad(6, 1, "2025", SquadMemberType.FIGHTER, listOf(8)),
        MemberSquad(7, 1, "2024", SquadMemberType.FIGHTER, listOf(7, 9)),
        MemberSquad(8, 1, "2024", SquadMemberType.FIGHTER),
        MemberSquad(6, 7, "2027", SquadMemberType.CANDIDATE),
        MemberSquad(9, 1, "2027", SquadMemberType.CANDIDATE),
    )


    override fun getSquads() = squads

    override fun getSquad(id: Int) = squads.find {it.id == id}

    override fun getMembers(IdSquad: Int): List<SquadMember> =
        links.filter { it.idSquad == IdSquad }.mapNotNull { link ->
            members.find { it.id == link.idMember }?.let { person ->
                SquadMember(
                    person.id,
                    person.photo,
                    listOf(person.name, person.surname)
                    .filter { it.isNotBlank() }
                    .joinToString(" "),
                    link.dateJoininig,
                    link.type,
                    positions.filter { it.id in link.positionIds }
                )
            }
        }

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

@DrawableRes
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
        "СПбГУ" -> R.drawable.ic_spbguptd
        "СПбГУТ" -> R.drawable.ic_spbgut
        "СЗГМУ" -> R.drawable.ic_szgmu
        "Технологический институт" -> R.drawable.ic_tu
        else -> R.drawable.ic_spbso
    }
}