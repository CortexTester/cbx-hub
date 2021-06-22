package cbx.hub.routing.model

import javax.persistence.*

@Entity
data class Dialect(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val name: String,
    @ManyToMany(mappedBy = "dialects", fetch = FetchType.LAZY)
    val party: MutableList<Party> = mutableListOf()

    )
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dialect_seq")
//    @SequenceGenerator(name = "dialect_seq", sequenceName = "dialect_seq", allocationSize = 100)
