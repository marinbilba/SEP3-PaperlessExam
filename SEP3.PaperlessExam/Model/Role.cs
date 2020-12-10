using System;
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

        public Role( string name)
        {
            Name = name;
        }

        protected bool Equals(Role other)
        {
            return Id == other.Id && Name == other.Name;
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Role) obj);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id, Name);
        }
    }
}