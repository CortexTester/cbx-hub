package cbx.hub.routing.model

import javax.persistence.*

@Entity
@Table(
    name = "Party",
    uniqueConstraints = [UniqueConstraint(columnNames = ["apiKey", "clientSidePartyId"])]
)
class Party(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var name: String,
//    @OneToMany(cascade = [CascadeType.ALL])
    @ManyToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    @JoinTable(
        name = "Party_Dialect",
        joinColumns = [JoinColumn(name = "party_id", referencedColumnName = "id", nullable = false, updatable = false)],
        inverseJoinColumns = [JoinColumn(name = "dialect_id", referencedColumnName = "id", nullable = false, updatable = false)]
    )
    var dialects: MutableList<Dialect> = mutableListOf(),
    var url: String,
    var clientSidePartyId: Long?,
    var apiKey: String


)

//    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "party_seq")
//    @SequenceGenerator(name = "party_seq", sequenceName = "party_seq", allocationSize = 100)

