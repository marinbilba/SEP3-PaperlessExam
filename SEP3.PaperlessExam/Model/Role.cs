using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model
{
    public class Role
    {
        [JsonPropertyName("id")] public int Id { get; set; }
        [JsonPropertyName("name")] public string Name { get; set; }

        public Role()
        {
        }

        public Role(int i, string name)
        {
            Id = i;
            Name = name;
        }


    }
}