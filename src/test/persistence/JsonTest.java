// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMember(String name, int bday, Member member) {
        assertEquals(name, member.getName());
        assertEquals(bday, member.getBday());
    }
}
